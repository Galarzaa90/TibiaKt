package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.forumAnnouncement
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.forums.ForumAnnouncement
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.parseAuthorTable


object ForumAnnouncementParser : Parser<ForumAnnouncement?> {
    override fun fromContent(content: String): ForumAnnouncement? {
        val boxContent = boxContent(content)
        return forumAnnouncement {
            val (sectionLink, boardLink) = boxContent.select("div.ForumBreadCrumbs > a").mapNotNull { it.getLinkInformation() }

            sectionId = sectionLink.queryParams["sectionid"]?.first()?.toInt() ?: throw ParsingException("Could not find section ID in link.")
            section = sectionLink.title
            boardId = boardLink.queryParams["boardid"]?.first()?.toInt() ?: throw ParsingException("Could not find board ID in link.")
            board = boardLink.title

            author = parseAuthorTable(boxContent.selectFirst("div.PostCharacterText")!!)
        }
    }
}