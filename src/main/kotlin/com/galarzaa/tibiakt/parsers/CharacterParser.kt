package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.core.parseTibiaTime
import com.galarzaa.tibiakt.models.Character
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

val deletedRegexp = Regex("([^,]+), will be deleted at (.*)")

object CharacterParser : Parser<Character> {
    override fun fromContent(content: String): Character? {
        val document: Document = Jsoup.parse(content, "", org.jsoup.parser.Parser.xmlParser())
        val boxContent = document.selectFirst("div.BoxContent")
        val tables = parseTables(boxContent!!)
        if (tables.containsKey("Character Information")) {
            return parseCharacterInformation(tables["Character Information"]!!)
        }
        return null
    }

    private fun parseCharacterInformation(rows: Elements): Character {
        val attributes = mutableMapOf<String, Any>()
        val charBuilder = Character.Builder()
        for (row: Element in rows) {
            val columns = row.select("td")
            var (field, value) = columns.map { it.text().trim() }
            field = field.replace(" ", "_").replace(":", "").lowercase()
            when (field) {
                "name" -> {
                    var name: String
                    val match = deletedRegexp.matchEntire(value)
                    if (match != null) {
                        name = match.groups[0]?.value!!
                    } else {
                        name = value
                    }
                    if (name.contains("(traded")) {
                        charBuilder.traded(true)
                        name = name.replace("(traded)", "").trim()
                    }
                    charBuilder.name(name)
                }
                "sex" -> charBuilder.sex(value)
                "vocation" -> charBuilder.vocation(value)
                "level" -> charBuilder.level(value.toInt())
                "world" -> charBuilder.world(value)
                "achievement_points" -> charBuilder.achievementPoints(value.toInt())
                "residence" -> charBuilder.residence(value)
                "last_login" -> {
                    if (!value.contains("never logged", true)) {
                        charBuilder.lastLogin(parseTibiaTime(value))
                    }
                }
            }

        }
        return charBuilder.build()
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