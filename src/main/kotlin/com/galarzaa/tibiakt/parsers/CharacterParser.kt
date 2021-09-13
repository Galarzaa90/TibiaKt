package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.core.getLinkInformation
import com.galarzaa.tibiakt.core.parsePopup
import com.galarzaa.tibiakt.core.parseTibiaDate
import com.galarzaa.tibiakt.core.parseTibiaDateTime
import com.galarzaa.tibiakt.models.Character
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

val deletedRegexp = Regex("([^,]+), will be deleted at (.*)")
val titlesRegexp = Regex("(.*)\\((\\d+) titles? unlocked\\)")
val houseRegexp = Regex("\\(([^)]+)\\) is paid until (.*)")

object CharacterParser : Parser<Character> {
    override fun fromContent(content: String): Character? {
        val document: Document = Jsoup.parse(content, "", org.jsoup.parser.Parser.xmlParser())
        val boxContent = document.selectFirst("div.BoxContent")
        val tables = parseTables(boxContent!!)
        val builder = Character.Builder()
        parseCharacterInformation(tables["Character Information"] ?: return null, builder)
        tables["Account Badges"]?.apply { parseAccountBadges(this, builder) }
        return builder.build()
    }

    private fun parseAccountBadges(rows: Elements, builder: Character.Builder) {
        val row = rows[0]
        for (column: Element in row.select("td")) {
            val popupSpan = column.select("span.HelperDivIndicator") ?: return
            val (title: String, popupContent: Document) = parsePopup(popupSpan.attr("onmouseover"))
            val description = popupContent.text()
            val imageUrl = column.selectFirst("img")?.attr("src")
            builder.addBadge(title, description, imageUrl!!)
        }
    }

    private fun parseCharacterInformation(rows: Elements, charBuilder: Character.Builder): Character.Builder {
        for (row: Element in rows) {
            val columns = row.select("td")
            var (field, value) = columns.map { it.wholeText().replace("\u00A0", " ").trim() }
            field = field.replace(" ", "_").replace(":", "").lowercase()
            when (field) {
                "name" -> parseNameField(charBuilder, value)
                "title" -> parseTitles(charBuilder, value)
                "former_names" -> charBuilder.formerNames(value.split(",").map { it.trim() })
                "former_world" -> charBuilder.formerWorld(value)
                "sex" -> charBuilder.sex(value)
                "vocation" -> charBuilder.vocation(value)
                "level" -> charBuilder.level(value.toInt())
                "achievement_points" -> charBuilder.achievementPoints(value.toInt())
                "world" -> charBuilder.world(value)
                "residence" -> charBuilder.residence(value)
                "last_login" -> {
                    if (!value.contains("never logged", true)) {
                        charBuilder.lastLogin(parseTibiaDateTime(value))
                    }
                }
                "comment" -> charBuilder.comment(value)
                "account_status" -> charBuilder.accountStatus(value)
                "house" -> parseHouseColumn(charBuilder, columns[1])
                "guild_membership" -> parseGuildColumn(charBuilder, columns[1])
            }
        }
        return charBuilder
    }

    private fun parseGuildColumn(charBuilder: Character.Builder, valueColumn: Element) {
        val link = valueColumn.selectFirst("a")?.getLinkInformation() ?: return
        val rankName = valueColumn.text().split("of the").first().trim()
        charBuilder.guild(rankName, link.title)
    }

    private fun parseHouseColumn(charBuilder: Character.Builder, valueColumn: Element) {
        val match = houseRegexp.find(valueColumn.ownText()) ?: return
        val (_, town, paidUntilStr) = match.groupValues
        val link = valueColumn.selectFirst("a")?.getLinkInformation() ?: return
        charBuilder.addHouse(
            link.title,
            link.queryParams["houseid"]?.first()?.toInt() ?: 0,
            town,
            parseTibiaDate(paidUntilStr)
        )
    }

    private fun parseTitles(charBuilder: Character.Builder, value: String) {
        val match = titlesRegexp.find(value) ?: return
        val (_, currentTitle, unlockedTitles) = match.groupValues
        charBuilder.titles(
            if (currentTitle.contains("none", true)) null else currentTitle.trim(),
            unlockedTitles.toInt()
        )
    }

    private fun parseNameField(charBuilder: Character.Builder, value: String) {
        val match = deletedRegexp.matchEntire(value)
        var name = if (match != null) {
            val (_, cleanName, deletionDateStr) = match.groupValues
            charBuilder.deletionDate(parseTibiaDateTime(deletionDateStr))
            cleanName
        } else {
            value
        }
        if (name.contains("(traded")) {
            charBuilder.recentlyTraded(true)
            name = name.replace("(traded)", "").trim()
        }
        charBuilder.name(name)
    }

    private fun parseTables(parsedContent: Element): Map<String, Elements> {
        val tables = parsedContent.select("div.TableContainer")
        val output = mutableMapOf<String, Elements>()
        for (table: Element in tables) {
            val captionContainer = table.selectFirst("div.CaptionContainer")
            val contentTable = table.selectFirst("table.TableContent")
            val caption = captionContainer!!.text()
            if (contentTable == null)
                continue
            val rows = contentTable.select("tr")
            output[caption] = rows
        }
        return output
    }


}