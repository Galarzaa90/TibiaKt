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

import com.galarzaa.tibiakt.core.builders.forumAnnouncement
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.forums.ForumAnnouncement
import com.galarzaa.tibiakt.core.html.cleanText
import com.galarzaa.tibiakt.core.html.getLinkInformation
import com.galarzaa.tibiakt.core.html.parseAuthorTable
import com.galarzaa.tibiakt.core.time.parseTibiaForumDateTime
import com.galarzaa.tibiakt.core.text.remove

/** Parser for forum announcements. */
public object ForumAnnouncementParser : Parser<ForumAnnouncement?> {
    override fun fromContent(content: String): ForumAnnouncement? = fromContent(content, 0)

    /**
     * Parse the content from Tibia.com into a forum announcement.
     *
     * @param announcementId The announcement ID to assign to the article, since it is not possible to know this only from the HTML content.
     */
    public fun fromContent(content: String, announcementId: Int): ForumAnnouncement? {
        val boxContent = boxContent(content)
        return forumAnnouncement {
            val breadCrumbs = boxContent.select("div.ForumBreadCrumbs > a")
            if (breadCrumbs.isEmpty()) {
                val messageBox = boxContent.selectFirst("div.TableContainer")
                if (messageBox == null || "error" !in messageBox.text().lowercase()) {
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
            this.announcementId = announcementId
            author = parseAuthorTable(boxContent.selectFirst("div.PostCharacterText")!!)


            val postContainer = boxContent.selectFirst("div.ForumPost")!!
            val postTextContainer = postContainer.selectFirst("div.PostText")!!
            val titleTag = postTextContainer.selectFirst("b")
            val datesContainer = postTextContainer.selectFirst("font")!!

            val (startDateText, endDateText) = datesContainer.cleanText().remove("(").remove(")").split(" until ")
            title = titleTag?.text() ?: throw ParsingException("Could not find title in post.")
            titleTag.remove()
            datesContainer.remove()

            this.content = postTextContainer.html().split("<hr>", limit = 2).last()
            startDate = parseTibiaForumDateTime(startDateText)
            endDate = parseTibiaForumDateTime(endDateText)

        }
    }
}
