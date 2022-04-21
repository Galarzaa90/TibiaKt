package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.ForumThreadBuilder
import com.galarzaa.tibiakt.core.builders.forumThread
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.forums.ForumEmoticon
import com.galarzaa.tibiakt.core.models.forums.ForumThread
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.parseAuthorTable
import com.galarzaa.tibiakt.core.utils.parsePagination
import com.galarzaa.tibiakt.core.utils.parseTibiaForumDate
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

object ForumThreadParser : Parser<ForumThread?> {
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
            section = sectionLink.title
            boardId = boardLink.queryParams["boardid"]?.first()?.toInt()
                ?: throw ParsingException("Could not find board ID in link.")
            board = boardLink.title

            val forumTitleContainer = boxContent.selectFirst("div.ForumTitleText")
            if (forumTitleContainer == null) {
                title = boxContent.selectFirst("div.ForumBreadCrumbs > b")?.text()
                    ?: throw ParsingException("Could not find partial title")
                threadId = 0
                currentPage = -1
                return@forumThread
            }
            val border = forumTitleContainer.parent()!!.previousSibling()!!.previousSibling()!!
            goldenFrame = "gold" in border.attr("style")
            title = forumTitleContainer.cleanText()

            val postTable = boxContent.selectFirst("table.TableContent")!!
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
            val contentContainer = container.selectFirst("div.PostText")
            val signatureContainer = container.selectFirst("td.ff_pagetext")
            if (signatureContainer != null) {
                signature = signatureContainer.html()
                signatureContainer.remove()
            }
            content = contentContainer!!.html()
            if (signatureContainer != null) {
                val parts = content!!.split(SIGNATURE_SEPARATOR)
                content = parts.subList(0, parts.size - 1).joinToString(SIGNATURE_SEPARATOR)
            }
            val (titleRaw, cleanContent) = content!!.split(Regex("""<br/?>\s*<br/?>"""), limit = 2)
            content = cleanContent
            val parsedTitle = Jsoup.parse(titleRaw)
            parsedTitle.selectFirst("img")?.let {
                emoticon = ForumEmoticon(it.attr("alt"), it.attr("src"))
            }
            parsedTitle.selectFirst("b")?.let {
                title = it.cleanText()
            }
            val postDetails = container.selectFirst("div.PostDetails")!!
            val dates = datesRegex.findAll(postDetails.text()).map { it.value }.toList()
            postedDate = parseTibiaForumDate(dates.first())
            if (dates.size > 1) {
                editedDate = parseTibiaForumDate(dates[1])
                editedBy = editedByRegex.find(postDetails.text())!!.groupValues[1]
            }
            val additionalBox = container.selectFirst("div.AdditionalBox")!!
            postId = additionalBox.cleanText().split("#", limit = 2)[1].toInt()
        }
    }
}