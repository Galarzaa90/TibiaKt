/*
 * Copyright © 2024 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.ForumThreadBuilder
import com.galarzaa.tibiakt.core.builders.forumThread
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.html.TABLE_SELECTOR
import com.galarzaa.tibiakt.core.html.cleanText
import com.galarzaa.tibiakt.core.html.getLinkInformation
import com.galarzaa.tibiakt.core.html.parseAuthorTable
import com.galarzaa.tibiakt.core.html.parsePagination
import com.galarzaa.tibiakt.core.models.forums.ForumEmoticon
import com.galarzaa.tibiakt.core.models.forums.ForumThread
import com.galarzaa.tibiakt.core.time.parseTibiaForumDateTime
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

/** Parser for forum threads. */
public object ForumThreadParser : Parser<ForumThread?> {
    private const val SIGNATURE_SEPARATOR = "________________"
    private val datesRegex = Regex("""(\d{2}\.\d{2}\.\d{4}\s\d{2}:\d{2}:\d{2})""")
    private val editedByRegex = Regex("""Edited by (.*) on \d{2}""")

    override fun fromContent(content: String): ForumThread? {
        val boxContent = boxContent(content)
        return forumThread {
            val breadCrumbs = boxContent.select("div.ForumBreadCrumbs > a")
            if (breadCrumbs.isEmpty()) {
                val messageBox = boxContent.selectFirst("div.InnerTableContainer")
                if (messageBox == null || "not found" !in messageBox.text()) {
                    throw ParsingException("Could not find Forum Breadcrumbs")
                }
                return null
            }
            val (sectionLink, boardLink) = breadCrumbs.mapNotNull { it.getLinkInformation() }

            sectionId = sectionLink.queryParams["sectionid"]?.first()?.toInt()
                ?: throw ParsingException("Could not find section ID in link.")
            sectionName = sectionLink.title
            boardId = boardLink.queryParams["boardid"]?.first()?.toInt()
                ?: throw ParsingException("Could not find board ID in link.")
            boardName = boardLink.title

            val forumTitleContainer = boxContent.selectFirst("div.ForumTitleText")
            if (forumTitleContainer == null) {
                title = boxContent.selectFirst("div.ForumBreadCrumbs > b")?.text()
                    ?: throw ParsingException("Could not find partial title")
                threadId = 0
                currentPage = -1
                return@forumThread
            }
            val border = forumTitleContainer.parent()!!.previousSibling()!!.previousSibling()!!
            hasGoldenFrame = "gold" in border.attr("style")
            title = forumTitleContainer.cleanText()

            val postTable = boxContent.selectFirst(TABLE_SELECTOR)!!
            val threadInfoContainer = postTable.selectFirst("div.ForumPostHeaderText")!!
            val (threadNumber, navigationContainer) = threadInfoContainer.childNodes()

            threadId = (threadNumber as TextNode).cleanText().split("#").last().toInt()

            val navigationLinks = (navigationContainer as Element).select("a").mapNotNull { it.getLinkInformation() }
            if (navigationLinks.size == 2) {
                val (prevLink, nextLink) = navigationLinks
                previousTopicNumber = prevLink.queryParams["threadid"]?.first()?.toInt()
                    ?: throw ParsingException("Could not find previous topic number in link.")
                nextTopicNumber = nextLink.queryParams["threadid"]?.first()?.toInt()
                    ?: throw ParsingException("Could not find next topic number in link.")
            } else if ("Previous" in navigationLinks.first().title) {
                previousTopicNumber = navigationLinks.first().queryParams["threadid"]?.first()?.toInt()
                    ?: throw ParsingException("Could not find previous topic number in link.")
            } else {
                nextTopicNumber = navigationLinks.first().queryParams["threadid"]?.first()?.toInt()
                    ?: throw ParsingException("Could not find next topic number in link.")
            }
            val paginationData = boxContent.selectFirst("td.PageNavigation")!!.parsePagination()
            totalPages = paginationData.totalPages
            currentPage = paginationData.currentPage
            resultsCount = paginationData.resultsCount

            val postContainers = postTable.select("div.PostBody")
            for (postContainer in postContainers) {
                parsePostContainer(postContainer)
            }
        }
    }

    private fun ForumThreadBuilder.parsePostContainer(container: Element) {
        addPost {
            author = parseAuthorTable(container.selectFirst("div.PostCharacterText")!!)
            val contentContainer = container.selectFirst("div.PostText")!!
            var emoticonTag: Element? = null
            var titleTag: Element? = null
            while (true) {
                val child = contentContainer.child(0)
                child.remove()
                when (child.tagName()) {
                    "img" -> emoticonTag = child
                    "b" -> titleTag = child
                    "div" -> break
                }
            }
            contentContainer.selectFirst("br")?.remove()

            val signatureContainer = container.selectFirst("td.ff_pagetext")
            if (signatureContainer != null) {
                signature = signatureContainer.html()
                signatureContainer.remove()
            }
            content = contentContainer.html()
            if (signatureContainer != null) {
                val parts = content!!.split(SIGNATURE_SEPARATOR)
                content = parts.subList(0, parts.size - 1).joinToString(SIGNATURE_SEPARATOR)
            }
            emoticonTag?.let {
                emoticon = ForumEmoticon(it.attr("alt"), it.attr("src"))
            }
            titleTag?.let {
                title = it.cleanText()
            }
            val postDetails = container.selectFirst("div.PostDetails")!!
            val dates = datesRegex.findAll(postDetails.text()).map { it.value }.toList()
            postedDate = parseTibiaForumDateTime(dates.first())
            if (dates.size > 1) {
                editedDate = parseTibiaForumDateTime(dates[1])
                editedBy = editedByRegex.find(postDetails.text())!!.groupValues[1]
            }
            val additionalBox = container.selectFirst("div.AdditionalBox")!!
            postId = additionalBox.cleanText().split("#", limit = 2)[1].toInt()
        }
    }
}
