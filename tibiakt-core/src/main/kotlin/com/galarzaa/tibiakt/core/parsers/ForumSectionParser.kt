package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.forumsSection
import com.galarzaa.tibiakt.core.builders.lastPost
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.forums.ForumSection
import com.galarzaa.tibiakt.core.utils.cells
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.parseTablesMap
import com.galarzaa.tibiakt.core.utils.parseTibiaForumDate
import com.galarzaa.tibiakt.core.utils.queryParams
import com.galarzaa.tibiakt.core.utils.remove
import java.net.URL
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


object ForumSectionParser : Parser<ForumSection> {
    override fun fromContent(content: String): ForumSection {
        val boxContent = boxContent(content)
        val tables = boxContent.parseTablesMap()
        if ("Boards" !in tables) throw ParsingException("Boards table not found")

        return forumsSection {
            val boardRows = tables["Boards"]!!.selectFirst("table.TableContent")?.select("tr:not(.LabelH)")!!
            for (row in boardRows) {
                val columns = row.cells()
                if (columns.size != 5) continue
                val boardColumn = columns[1]
                val boardLink = boardColumn.selectFirst("a")?.getLinkInformation()!!




                addEntry {
                    name = boardLink.title
                    boardId = boardLink.queryParams["boardid"]!!.first().toInt()
                    description = boardColumn.selectFirst("font")?.cleanText() ?: ""
                    posts = columns[2].text().remove(",").toInt()
                    threads = columns[2].text().remove(",").toInt()
                    lastPost = columns[4].selectFirst("div.LastPostInfo")?.let {
                        val permalink = it.selectFirst("a")?.getLinkInformation() ?: return@let null
                        val authorTag = columns[4].selectFirst("font") ?: return@let null
                        val authorLink = authorTag.selectFirst("font > a")?.getLinkInformation()
                        var authorName = authorTag.cleanText().removePrefix("by ")
                        var isTraded = false
                        if("(traded)" in authorName) {
                            authorName = authorName.remove("(traded)")
                            isTraded = true
                        }
                        lastPost {
                            postId = permalink.queryParams["postid"]!!.first().toInt()
                            date = parseTibiaForumDate(it.cleanText())
                            author = authorName
                            deleted = authorLink == null
                            traded = isTraded
                        }
                    }
                }
            }
            // Try to get section ID from login link
            sectionId = boxContent.selectFirst("p.ForumWelcome > a")?.getLinkInformation()?.let {
                val query = it.queryParams["redirect"]?.first() ?: return@let null
                val decodedUrl = URL(URLDecoder.decode(query, StandardCharsets.UTF_8))
                decodedUrl.queryParams()["sectionid"]?.first()?.toInt()
            } ?: 0
        }
    }
}