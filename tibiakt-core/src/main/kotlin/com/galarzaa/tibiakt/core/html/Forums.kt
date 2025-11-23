/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.tibiakt.core.html

import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.section.community.guild.model.GuildMembershipWithTitle
import com.galarzaa.tibiakt.core.section.forum.shared.builder.lastPost
import com.galarzaa.tibiakt.core.section.forum.shared.model.ForumAuthor
import com.galarzaa.tibiakt.core.section.forum.shared.model.LastPost
import com.galarzaa.tibiakt.core.text.findInteger
import com.galarzaa.tibiakt.core.text.parseInteger
import com.galarzaa.tibiakt.core.text.remove
import com.galarzaa.tibiakt.core.time.parseTibiaForumDateTime
import org.jsoup.nodes.Element


internal const val TRADED_TAG = "(traded)"

internal fun parseLastPostFromCell(cell: Element): LastPost? {
    val postDate = cell.selectFirst("span.LastPostInfo") ?: return null
    val permalink = cell.selectFirst("a")?.getLinkInformation() ?: return null
    val authorTag = cell.selectFirst("font") ?: return null
    val authorLink = authorTag.selectFirst("a")?.getLinkInformation()
    var authorName = authorTag.cleanText().removePrefix("by ")
    var isTraded = false
    if (TRADED_TAG in authorName) {
        authorName = authorName.remove(TRADED_TAG)
        isTraded = true
    }
    return lastPost {
        postId = permalink.queryParams["postid"]!!.first().toInt()
        postedAt = parseTibiaForumDateTime(postDate.cleanText())
        this.authorName = authorName
        isDeleted = authorLink == null && !isTraded
        this.isTraded = isTraded
    }
}

private val titles =
    listOf("Tutor", "Community Manager", "Customer Support", "Programmer", "Game Content Designer", "Tester")
private val charInfoRegex = Regex("""Inhabitant of (\w+)\nVocation: ([\w\s]+)\nLevel: (\d+)""")
private val authorPostsRegex = Regex("""Posts: (\d+)""")

private val titleRegex = Regex("""\(([\w\s]+)\)""")

internal fun parseAuthorTable(table: Element): ForumAuthor {
    val charLink = table.selectFirst("a")?.getLinkInformation()
    if (charLink == null) {
        val name = table.cleanText()
        val isTraded: Boolean = name.contains(TRADED_TAG)
        return ForumAuthor.Unavailable(name.remove(TRADED_TAG).trim(), !isTraded, isTraded)
    }
    val charInfo = table.selectFirst("font.ff_infotext")
    val positionInfo = table.selectFirst("font.ff_smallinfo")
    var title: String? = null
    var position: String? = null
    var isRecentlyTraded = false
    if (positionInfo != null && (charInfo == null || positionInfo.parent() != charInfo)) {
        positionInfo.replaceBrs().wholeCleanText().split("\n").forEach {
            if (it in titles) position = it
            else if ("traded" in it) isRecentlyTraded = true
            else title = it
        }
    }
    var guildMembership: GuildMembershipWithTitle? = null
    charInfo?.selectFirst("font.ff_smallinfo")?.let {
        val guildLink = it.selectFirst("a")
        val guildLinkInfo = guildLink?.getLinkInformation()!!
        val guildName = guildLinkInfo.title
        guildLink.remove()
        var rank = it.cleanText()
        val guildTitle = titleRegex.find(rank)?.groupValues?.get(1)?.also { g -> rank = rank.remove("($g)").trim() }
        rank = rank.removeSuffix("of the").trim()
        guildMembership = GuildMembershipWithTitle(guildName, rank, guildTitle)
    }
    charInfo?.replaceBrs()
    if (charInfo?.cleanText()?.contains("Tournament - ") == true) {
        return ForumAuthor.Tournament(charLink.title, charInfo.cleanText().findInteger())
    }

    val (_, world, vocation, level) = charInfoRegex.find(charInfo!!.wholeCleanText())?.groupValues
        ?: throw ParsingException("Could not find character info")

    val (_, postsText) = authorPostsRegex.find(charInfo.wholeCleanText())!!.groupValues

    return ForumAuthor.Character(
        name = charLink.title,
        level = level.toInt(),
        world = world,
        vocation = StringEnum.fromValue(vocation) ?: throw ParsingException("Unknown vocation: $vocation"),
        position = position,
        title = title,
        guild = guildMembership,
        postsCount = postsText.parseInteger(),
        isRecentlyTraded = isRecentlyTraded
    )
}
