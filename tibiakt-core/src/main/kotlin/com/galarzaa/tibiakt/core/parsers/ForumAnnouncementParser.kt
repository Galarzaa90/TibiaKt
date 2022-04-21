package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.forumAnnouncement
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.forums.ForumAnnouncement
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.parseAuthorTable
import com.galarzaa.tibiakt.core.utils.parseTibiaForumDate
import com.galarzaa.tibiakt.core.utils.remove


object ForumAnnouncementParser : Parser<ForumAnnouncement?> {
    override fun fromContent(content: String) = fromContent(content, 0)

    fun fromContent(content: String, announcementId: Int): ForumAnnouncement? {
        val boxContent = boxContent(content)
        return forumAnnouncement {
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
            startDate = parseTibiaForumDate(startDateText)
            endDate = parseTibiaForumDate(endDateText)

        }
    }
}