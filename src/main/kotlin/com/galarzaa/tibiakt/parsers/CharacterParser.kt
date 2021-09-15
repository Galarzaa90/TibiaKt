package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.ParsingException
import com.galarzaa.tibiakt.core.*
import com.galarzaa.tibiakt.models.Character
import com.galarzaa.tibiakt.models.Killer
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.time.Instant


object CharacterParser : Parser<Character> {
    private val deletedRegexp = Regex("""([^,]+), will be deleted at (.*)""")
    private val titlesRegexp = Regex("""(.*)\((\d+) titles? unlocked\)""")
    private val houseRegexp = Regex("""\(([^)]+)\) is paid until (.*)""")
    private val deathsRegex = Regex("""Level (\d+) by (.*)\.</td>""")
    private val deathAssistsRegex = Regex("""(?<killers>.+)\.<br\s?/>Assisted by (?<assists>.+)""")
    private val linkSearch = Regex("""<a[^>]+>[^<]+</a>""")
    private val linkContent = Regex(""">([^<]+)<""")
    private val deathSummon = Regex("""(?<summon>.+) of <a[^>]+>(?<name>[^<]+)</a>""")
    private const val tradedLabel = "(traded)"

    override fun fromContent(content: String): Character? {
        val document: Document = Jsoup.parse(content, "", org.jsoup.parser.Parser.xmlParser())
        val boxContent = document.selectFirst("div.BoxContent")
        val tables = parseTables(boxContent ?: throw ParsingException("BoxContent container not found"))
        val builder = Character.Builder()
        parseCharacterInformation(tables["Character Information"] ?: return null, builder)
        tables["Account Badges"]?.apply { parseAccountBadges(this, builder) }
        tables["Account Achievements"]?.apply { parseAccountAchievements(this, builder) }
        tables["Account Information"]?.apply { parseAccountInformation(this, builder) }
        tables["Character Deaths"]?.apply { parseCharacterDeaths(this, builder) }
        tables["Characters"]?.apply { parseCharacters(this, builder) }
        return builder.build()
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
                "position" -> charBuilder.position(value)
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
        if (name.contains(tradedLabel)) {
            charBuilder.recentlyTraded(true)
            name = name.replace(tradedLabel, "").trim()
        }
        charBuilder.name(name)
    }

    private fun parseAccountBadges(rows: Elements, builder: Character.Builder) {
        val row = rows[0]
        for (column: Element in row.select("td")) {
            val popupSpan = column.select("span.HelperDivIndicator") ?: return
            val (title: String, popupContent: Document) = parsePopup(popupSpan.attr("onmouseover"))
            val description = popupContent.text()
            val imageUrl = column.selectFirst("img")?.attr("src")
            builder.addBadge(title, description, imageUrl ?: continue)
        }
    }

    private fun parseAccountAchievements(rows: Elements, builder: Character.Builder) {
        for (row: Element in rows) {
            val columns = row.select("td")
            if (columns.size != 2)
                continue
            val (gradeColumn, nameColumn) = columns
            val grade = gradeColumn.select("img").size
            val name = nameColumn.text()
            val secret = nameColumn.selectFirst("img") != null
            builder.addAchievement(name, grade, secret)
        }
    }

    private fun parseAccountInformation(rows: Elements, builder: Character.Builder) {
        val valueMap = mutableMapOf<String, String>()
        for (row: Element in rows) {
            val columns = row.select("td")
            var (field, value) = columns.map { it.wholeText().replace("\u00A0", " ").trim() }
            field = field.replace(" ", "_").replace(":", "").lowercase()
            when (field) {
                "position" -> {
                    if (value.contains("tutor", true)) {
                        valueMap["stars"] = columns[1].select("img").size.toString()
                    }
                    valueMap[field] = value
                }
                else -> valueMap[field] = value
            }
        }
        builder.accountInformation(
            created = parseTibiaDateTime(valueMap["created"] ?: return),
            loyaltyTitle = valueMap["loyalty_title"],
            position = valueMap["position"],
            tutorStars = valueMap["stars"]?.toInt()
        )
    }

    private fun parseCharacterDeaths(rows: Elements, builder: Character.Builder) {
        for (row: Element in rows) {
            val columns = row.select("td")
            if (columns.size != 2)
                continue
            val (dateColumn, descriptionColumn) = columns
            val deathDateTime: Instant = parseTibiaDateTime(dateColumn.text())
            val deathMatch = deathsRegex.find(descriptionColumn.toString()) ?: continue
            var (_, levelStr, killersDesc) = deathMatch.groupValues
            var assistNameList: List<String> = mutableListOf()
            deathAssistsRegex.find(killersDesc)?.apply {
                killersDesc = this.groups["killers"]?.value ?: return
                val assistsDec = this.groups["assists"]?.value ?: return
                assistNameList = linkSearch.findAll(assistsDec).map { it.value }.toList()
            }
            val killerNameList = killersDesc.splitList()
            val killerList = killerNameList.mapNotNull { parseKiller(it) }
            val assistsList = assistNameList.mapNotNull { a ->
                linkContent.find(a)?.groups?.get(1)?.value?.let {
                    Killer(it.clean(), true)
                }
            }
            builder.addDeath(deathDateTime, levelStr.toInt(), killerList, assistsList)
        }
    }

    private fun parseKiller(killerHtml: String): Killer? {
        var name: String
        var player = false
        var traded = false
        var summon: String? = null
        if (killerHtml.contains("href")) {
            name = linkContent.find(killerHtml)?.groups?.get(1)?.value ?: return null
            player = true
        } else {
            name = killerHtml
        }
        deathSummon.find(killerHtml)?.apply {
            summon = this.groups.get("summon")?.value?.clean()
        }
        name = name.clean()
        if (name.contains(tradedLabel)) {
            name = name.replace(tradedLabel, "").trim()
            traded = true
        }
        return Killer(name, player, summon, traded)
    }

    private fun parseCharacters(rows: Elements, builder: Character.Builder) {
        for (row: Element in rows.subList(1, rows.lastIndex)) {
            val columns = row.select("td")
            if (columns.size != 4)
                continue
            val (nameColumn, worldColumn, statusColumn, _) = columns
            var traded = false
            var name = nameColumn.text().splitList(".").last().clean()
            if (name.contains(tradedLabel, true)) {
                name = name.replace(tradedLabel, "").trim()
                traded = true
            }
            val main = nameColumn.selectFirst("img") != null
            val world = worldColumn.text().clean()
            val status = statusColumn.text().clean()
            val online = status.contains("online")
            val deleted = status.contains("deleted")
            val position = if (status.contains("CipSoft Member")) "CipSoft Member" else null
            builder.addCharacter(name, world, main, online, deleted, traded, position)
        }
    }

    private fun parseTables(parsedContent: Element): Map<String, Elements> {
        val tables = parsedContent.select("div.TableContainer")
        val output = mutableMapOf<String, Elements>()
        for (table: Element in tables) {
            val captionContainer = table.selectFirst("div.CaptionContainer")
            val contentTable = table.selectFirst("table.TableContent")
            val caption = captionContainer?.text() ?: throw ParsingException("table has no caption container")
            if (contentTable == null)
                continue
            val rows = contentTable.select("tr")
            output[caption] = rows
        }
        return output
    }
}
