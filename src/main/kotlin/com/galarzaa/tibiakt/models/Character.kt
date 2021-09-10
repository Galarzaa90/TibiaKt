package com.galarzaa.tibiakt.models
import org.jsoup.Jsoup
import org.jsoup.Jsoup.*
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import org.jsoup.select.Elements

class Character (val name: String, val level: Int){
    companion object {
        fun parseFromContent(content: String): Character? {
            val document: Document = Jsoup.parse(content, "", Parser.xmlParser())
            val boxContent = document.selectFirst("div.BoxContent")
            val tables = parseTables(boxContent!!)
            if (tables.containsKey("Character Information")) {
                return parseCharacterInformation(tables["Character Information"]!!)
            }
            return null;
        }

        private fun parseCharacterInformation(rows: Elements): Character {
            val attributes = mutableMapOf<String, String>()
            for (row: Element in rows){
                val columns = row.select("td")
                var (field, value) = columns.map{ it.text().trim()}
                field = field.replace(" ", "_").replace(":", "").lowercase()
                attributes[field] = value
            }
            val character = Character(
                name = attributes["name"]!!,
                level = attributes["level"]!!.toInt(),
            )
            return character
        }

        private fun parseTables(parsedContent: Element) : Map<String, Elements> {
            val tables = parsedContent.select("div.TableContainer")
            val output = mutableMapOf<String, Elements>()
            for (table: Element in tables){
                val captionContainer = table.selectFirst("div.CaptionContainer")
                val contentTable = table.selectFirst("table.TableContent")
                val caption = captionContainer!!.text()
                if(contentTable == null)
                    continue
                val rows = contentTable.select("tr")
                output[caption] = rows
            }
            return output
        }
    }
}