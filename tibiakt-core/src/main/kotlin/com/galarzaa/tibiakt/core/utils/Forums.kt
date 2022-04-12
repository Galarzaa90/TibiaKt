package com.galarzaa.tibiakt.core.utils

import com.galarzaa.tibiakt.core.builders.lastPost
import com.galarzaa.tibiakt.core.models.forums.ForumAuthor
import com.galarzaa.tibiakt.core.models.forums.LastPost
import org.jsoup.nodes.Element

internal fun parseLastPostFromCell(cell: Element): LastPost? {
    val postDate = cell.selectFirst("div.LastPostInfo") ?: return null
    val permalink = cell.selectFirst("a")?.getLinkInformation() ?: return null
    val authorTag = cell.selectFirst("font") ?: return null
    val authorLink = authorTag.selectFirst("font > a")?.getLinkInformation()
    var authorName = authorTag.cleanText().removePrefix("by ")
    var isTraded = false
    if ("(traded)" in authorName) {
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

val titles = listOf("Tutor", "Community Manager", "Customer Support", "Programmer", "Game Content Designer", "Tester")
val charInfoRegex = Regex("""Inhabitant of (\w+)\nVocation: ([\w\s]+)\nLevel: (\d+)""")

internal fun parseAuthorTable(table: Element): ForumAuthor {
    val charLink = table.selectFirst("a")?.getLinkInformation()
    if (charLink == null) {
        val name = table.cleanText()
        val traded: Boolean = name.contains("(traded)")
        return ForumAuthor.Unavailable(name.remove("(traded)").trim(), !traded, traded)
    }
    val positionInfo = table.selectFirst("font.ff_smallinfo")
    var title: String? = null
    var position: String? = null
    if (positionInfo != null && positionInfo.parent() == table) {
        positionInfo.replaceBrs()
        positionInfo.cleanText().split("\n").forEach {
            if (it in titles) position = it
            else title = it
        }
    }
    val charInfo = table.selectFirst("font.ff_infotext")!!
    val guildInfo = charInfo.selectFirst("font.ff_smallinfo")!!
    charInfo.replaceBrs()


    val
    val charInfoRegex.find(char)?.groups

    return ForumAuthor.Unavailable(name.remove("(traded)").trim(), !traded, traded)
}