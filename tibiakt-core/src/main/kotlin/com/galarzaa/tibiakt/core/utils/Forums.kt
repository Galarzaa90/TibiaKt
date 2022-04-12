package com.galarzaa.tibiakt.core.utils

import com.galarzaa.tibiakt.core.builders.lastPost
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.forums.BaseForumAuthor
import com.galarzaa.tibiakt.core.models.forums.ForumAuthor
import com.galarzaa.tibiakt.core.models.forums.LastPost
import com.galarzaa.tibiakt.core.models.forums.UnavailableForumAuthor
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
val authorPostsRegex = Regex("""Posts: (\d+)""")

internal fun parseAuthorTable(table: Element): BaseForumAuthor {
    val charLink = table.selectFirst("a")?.getLinkInformation()
    if (charLink == null) {
        val name = table.cleanText()
        val traded: Boolean = name.contains("(traded)")
        return UnavailableForumAuthor(name.remove("(traded)").trim(), !traded, traded)
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
    val guildInfo = charInfo.selectFirst("font.ff_smallinfo")
    charInfo.replaceBrs()

    val (_, world, vocation, level) = charInfoRegex.find(charInfo.wholeCleanText())?.groupValues
        ?: throw ParsingException("Could not find character info")

    val (_, postsText) = authorPostsRegex.find(charInfo.wholeCleanText())!!.groupValues

    return ForumAuthor(
        name = charLink.title,
        level = level.toInt(),
        world = world,
        vocation = StringEnum.fromValue(vocation) ?: throw ParsingException("Unknown vocation: $vocation"),
        position = position,
        title = title,
        guild = null,
        posts = postsText.parseInteger())
}