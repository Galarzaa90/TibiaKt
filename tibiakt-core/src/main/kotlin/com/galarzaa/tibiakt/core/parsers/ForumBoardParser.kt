package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.ForumBoardBuilder
import com.galarzaa.tibiakt.core.builders.forumBoard
import com.galarzaa.tibiakt.core.enums.ThreadStatus
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.forums.ForumBoard
import com.galarzaa.tibiakt.core.models.forums.ForumEmoticon
import com.galarzaa.tibiakt.core.utils.cells
import com.galarzaa.tibiakt.core.utils.cleanText
import com.galarzaa.tibiakt.core.utils.formData
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.offsetStart
import com.galarzaa.tibiakt.core.utils.parseLastPostFromCell
import com.galarzaa.tibiakt.core.utils.parsePagination
import com.galarzaa.tibiakt.core.utils.remove
import com.galarzaa.tibiakt.core.utils.rows
import org.jsoup.nodes.Element

public object ForumBoardParser : Parser<ForumBoard?> {
    private val fileNameRegex = Regex("""([\w_]+.gif)""")
    override fun fromContent(content: String): ForumBoard? {
        val boxContent = boxContent(content, org.jsoup.parser.Parser.xmlParser())
        val forumBreadcrumbs = boxContent.selectFirst("div.ForumBreadCrumbs")
        if (forumBreadcrumbs == null) {
            boxContent.selectFirst("div.InnerTableContainer")?.takeIf { "board you requested" in it.cleanText() }
                ?: throw ParsingException("content does not belong to a board.")
            return null
        }
        return forumBoard {
            val headerText = forumBreadcrumbs.cleanText()
            val headerParts = headerText.split("|").map { it.trim() }
            name = headerParts.last()
            section = headerParts.first()
            sectionId =
                forumBreadcrumbs.selectFirst("a")?.getLinkInformation()?.queryParams?.get("sectionid")?.first()?.toInt()
                    ?: throw ParsingException("sectionId not found")

            val form = boxContent.selectFirst("form")?.formData() ?: throw ParsingException("form not found")
            boardId = form.values["boardid"]?.toInt()
            threadAge = form.values["threadage"]?.toInt() ?: return@forumBoard

            val table = boxContent.selectFirst("table.Table3") ?: throw ParsingException("No board tables found.")
            val contentTables = table.select("table.TableContent")
            if (contentTables.size >= 2) parseAnnouncements(contentTables.first()!!)
            parseThreadsTable(contentTables.last()!!)
            val paginationData = boxContent.selectFirst("td > small")!!.parsePagination()
            currentPage = paginationData.currentPage
            totalPages = paginationData.totalPages
            resultsCount = paginationData.resultsCount
        }
    }

    private fun ForumBoardBuilder.parseThreadsTable(table: Element) {
        for (row in table.rows().offsetStart(1)) {
            val columns = row.cells()
            if (columns.size < 6) continue
            thread {
                val (threadLink, pageLinks) = columns[2].select("a").mapNotNull { it.getLinkInformation() }
                    .let { it.first() to it.drop(1) }
                var authorName = columns[3].cleanText()
                columns[1].selectFirst("img")?.also {
                    emoticon = ForumEmoticon(it.attr("alt"), it.attr("src"))
                }
                val isTraded: Boolean
                if ("(traded)" in authorName) {
                    authorName = authorName.replace("(traded)", "").trim()
                    isTraded = true
                } else {
                    isTraded = false
                }
                val statusImageFileName = columns[0].selectFirst("img")?.attr("src")?.let {
                    fileNameRegex.find(it)?.groupValues?.get(1)
                } ?: ""
                val authorLink = columns[3].selectFirst("a")

                title = threadLink.title
                threadId = threadLink.queryParams["threadid"]?.get(0)?.toInt()
                pages =
                    pageLinks.takeIf { it.isNotEmpty() }?.last()?.queryParams?.get("pagenumber")?.get(0)?.toInt() ?: 1
                author = authorName
                authorTraded = isTraded
                authorDeleted = authorLink == null && !isTraded
                ThreadStatus.values().filter { it.name.lowercase() in statusImageFileName }.forEach { status.add(it) }
                lastPost = parseLastPostFromCell(columns[6]) ?: throw ParsingException("last post not found")
                replies = columns[4].text().remove(",").toInt()
                views = columns[5].text().remove(",").toInt()
                goldenFrame = "ClassifiedProposal" in row.attr("class")
            }
        }
    }

    private fun ForumBoardBuilder.parseAnnouncements(table: Element) {
        for (row in table.rows().offsetStart(1)) {
            val (authorLink, titleLink) = row.select("a").mapNotNull { it.getLinkInformation() }
            announcement {
                title = titleLink.title
                author = authorLink.title
                announcementId = titleLink.queryParams["announcementid"]?.first()?.toInt()
            }
        }
    }
}
