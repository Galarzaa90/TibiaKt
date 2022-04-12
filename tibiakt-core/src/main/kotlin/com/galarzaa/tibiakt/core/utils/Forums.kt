package com.galarzaa.tibiakt.core.utils

import com.galarzaa.tibiakt.core.builders.lastPost
import com.galarzaa.tibiakt.core.models.forums.ForumAuthor
import com.galarzaa.tibiakt.core.models.forums.LastPost
import org.jsoup.nodes.Element

internal fun parseLastPostFromCell(cell: Element): LastPost? {
    val postDate = cell.selectFirst("div.LastPostInfo") ?: return null
    val permalink = cell.selectFirst("a")?.getLinkInformation() ?: return null
    val authorTag =cell.selectFirst("font") ?: return null
    val authorLink = authorTag.selectFirst("font > a")?.getLinkInformation()
    var authorName = authorTag.cleanText().removePrefix("by ")
    var isTraded = false
    if("(traded)" in authorName) {
        authorName = authorName.remove("(traded)")
        isTraded = true
    }
    return lastPost {
        postId = permalink.queryParams["postid"]!!.first().toInt()
        date = parseTibiaForumDate(postDate.cleanText())
        author = authorName
        deleted = authorLink == null && !isTraded
        traded = isTraded
    }
}

internal fun parseAuthorTable(table: Element): ForumAuthor {
    val charLink = table.selectFirst("a")?.getLinkInformation()
    if(charLink == null) {
        val name = table.cleanText()
        val traded: Boolean = name.contains("(traded)")
        return ForumAuthor.Unavailable(name.remove("(traded)").trim(), !traded, traded)
    }
    return ForumAuthor.Unavailable("", false, false)
}