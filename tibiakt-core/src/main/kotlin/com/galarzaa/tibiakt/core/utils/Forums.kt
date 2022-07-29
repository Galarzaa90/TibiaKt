/*
 * Copyright © 2022 Allan Galarza
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

package com.galarzaa.tibiakt.core.utils

import com.galarzaa.tibiakt.core.builders.lastPost
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.character.GuildMembership
import com.galarzaa.tibiakt.core.models.forums.BaseForumAuthor
import com.galarzaa.tibiakt.core.models.forums.ForumAuthor
import com.galarzaa.tibiakt.core.models.forums.LastPost
import com.galarzaa.tibiakt.core.models.forums.TournamentForumAuthor
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
        date = parseTibiaForumDateTime(postDate.cleanText())
        author = authorName
        deleted = authorLink == null && !isTraded
        traded = isTraded
    }
}

private val titles =
    listOf("Tutor", "Community Manager", "Customer Support", "Programmer", "Game Content Designer", "Tester")
private val charInfoRegex = Regex("""Inhabitant of (\w+)\nVocation: ([\w\s]+)\nLevel: (\d+)""")
private val authorPostsRegex = Regex("""Posts: (\d+)""")

internal fun parseAuthorTable(table: Element): BaseForumAuthor {
    val charLink = table.selectFirst("a")?.getLinkInformation()
    if (charLink == null) {
        val name = table.cleanText()
        val traded: Boolean = name.contains("(traded)")
        return UnavailableForumAuthor(name.remove("(traded)").trim(), !traded, traded)
    }
    val positionInfo = table.select("font.ff_smallinfo")
    var title: String? = null
    var position: String? = null
    var recentlyTraded = false
    positionInfo.filter { it.parent() == table }.forEach { element ->
        element.replaceBrs().wholeCleanText().split("\n").forEach {
            if (it in titles) position = it
            else if ("traded" in it) recentlyTraded = true
            else title = it
        }
    }
    var guildMembership: GuildMembership? = null
    val charInfo = table.selectFirst("font.ff_infotext")!!
    charInfo.selectFirst("font.ff_smallinfo")?.let {
        val guildLink = it.selectFirst("a")
        val guildLinkInfo = guildLink?.getLinkInformation()!!
        val guildName = guildLinkInfo.title
        guildLink.remove()
        val rank = it.cleanText().removeSuffix("of the").trim()
        guildMembership = GuildMembership(guildName, rank)
    }
    charInfo.replaceBrs()
    if ("Tournament - " in charInfo.cleanText()) {
        return TournamentForumAuthor(charLink.title, charInfo.cleanText().findInteger())
    }

    val (_, world, vocation, level) = charInfoRegex.find(charInfo.wholeCleanText())?.groupValues
        ?: throw ParsingException("Could not find character info")

    val (_, postsText) = authorPostsRegex.find(charInfo.wholeCleanText())!!.groupValues

    return ForumAuthor(name = charLink.title,
        level = level.toInt(),
        world = world,
        vocation = StringEnum.fromValue(vocation) ?: throw ParsingException("Unknown vocation: $vocation"),
        position = position,
        title = title,
        guild = guildMembership,
        posts = postsText.parseInteger(),
        isRecentlyTraded = recentlyTraded)
}