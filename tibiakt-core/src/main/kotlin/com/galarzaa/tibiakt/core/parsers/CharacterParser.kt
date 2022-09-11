package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.builders.CharacterBuilder
import com.galarzaa.tibiakt.core.builders.character
import com.galarzaa.tibiakt.core.enums.StringEnum
import com.galarzaa.tibiakt.core.exceptions.ParsingException
import com.galarzaa.tibiakt.core.models.character.Character
import com.galarzaa.tibiakt.core.models.character.Killer
import com.galarzaa.tibiakt.core.utils.clean
import com.galarzaa.tibiakt.core.utils.getLinkInformation
import com.galarzaa.tibiakt.core.utils.parsePopup
import com.galarzaa.tibiakt.core.utils.parseTables
import com.galarzaa.tibiakt.core.utils.parseTibiaDate
import com.galarzaa.tibiakt.core.utils.parseTibiaDateTime
import com.galarzaa.tibiakt.core.utils.remove
import com.galarzaa.tibiakt.core.utils.splitList
import kotlinx.datetime.Instant
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements


public object CharacterParser : Parser<Character?> {
    private val deletedRegexp = Regex("""([^,]+), will be deleted at (.*)""")
    private val titlesRegexp = Regex("""(.*)\((\d+) titles? unlocked\)""")
    private val houseRegexp = Regex("""\(([^)]+)\) is paid until (.*)""")
    private val deathsRegex = Regex("""Level (\d+) by (.*)\.</td>""")
    private val deathAssistsRegex = Regex("""(?:(?<killers>.+)\.<br\s?/>)?Assisted by (?<assists>.+)""")
    private val linkSearch = Regex("""<a[^>]+>[^<]+</a>""")
    private val linkContent = Regex(""">([^<]+)<""")
    private val deathSummon = Regex("""(?<summon>an? .+) of (?<name>.*)""")
    private const val tradedLabel = "(traded)"

    override fun fromContent(content: String): Character? {
        val document: Document = Jsoup.parse(content, "", org.jsoup.parser.Parser.xmlParser())
        val boxContent =
            document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")
        val tables = boxContent.parseTables()
        if (tables.keys.any { it.startsWith("Could not find character") }) return null
        return character {
            parseCharacterInformation(tables["Character Information"] ?: return null)
            tables["Account Badges"]?.apply { parseAccountBadges(this) }
            tables["Account Achievements"]?.apply { parseAccountAchievements(this) }
            tables["Account Information"]?.apply { parseAccountInformation(this) }
            tables["Character Deaths"]?.apply { parseCharacterDeaths(this) }
            tables["Characters"]?.apply { parseCharacters(this) }
        }

    }

    private fun CharacterBuilder.parseCharacterInformation(rows: Elements) {
        for (row: Element in rows) {
            val columns = row.select("td")
            var (field, value) = columns.map { it.wholeText().clean() }
            field = field.replace(" ", "_").remove(":").lowercase()
            when (field) {
                "name" -> parseNameField(value)
                "title" -> parseTitles(value)
                "former_names" -> formerNames = value.split(",").map { it.trim() }
                "former_world" -> formerWorld = value
                "sex" -> sex = StringEnum.fromValue(value)!!
                "vocation" -> vocation =
                    StringEnum.fromValue(value) ?: throw ParsingException("Unknown vocation: $value")

                "level" -> level = value.toInt()
                "achievement_points" -> achievementPoints = value.toInt()
                "world" -> world = value
                "residence" -> residence = value
                "last_login" -> {
                    if (!value.contains("never logged", true)) {
                        lastLogin = parseTibiaDateTime(value)
                    }
                }
                "position" -> position = value
                "comment" -> comment = value
                "account_status" -> accountStatus =
                    StringEnum.fromValue(value) ?: throw ParsingException("Unknown account status: $value")
                "married_to" -> marriedTo = value
                "house" -> parseHouseColumn(columns[1])
                "guild_membership" -> parseGuildColumn(columns[1])
            }
        }
    }

    private fun CharacterBuilder.parseGuildColumn(valueColumn: Element) {
        val guildName = valueColumn.selectFirst("a")?.text() ?: return
        val rankName = valueColumn.text().split("of the").first().trim()
        guild(rankName, guildName)
    }

    private fun CharacterBuilder.parseHouseColumn(valueColumn: Element) {
        val match = houseRegexp.find(valueColumn.ownText()) ?: return
        val (_, town, paidUntilStr) = match.groupValues
        val link = valueColumn.selectFirst("a")?.getLinkInformation() ?: return
        addHouse(name = link.title,
            houseId = link.queryParams["houseid"]?.first()?.toInt() ?: return,
            town = town,
            paidUntil = parseTibiaDate(paidUntilStr),
            world = link.queryParams["world"]?.first() ?: return)
    }

    private fun CharacterBuilder.parseTitles(value: String) {
        val match = titlesRegexp.find(value) ?: return
        val (_, currentTitle, unlockedTitlesStr) = match.groupValues
        title = if (currentTitle.contains("none", true)) null else currentTitle.trim()
        unlockedTitles = unlockedTitlesStr.toInt()
    }

    private fun CharacterBuilder.parseNameField(value: String) {
        val match = deletedRegexp.matchEntire(value)
        name = if (match != null) {
            val (_, cleanName, deletionDateStr) = match.groupValues
            deletionDate = parseTibiaDateTime(deletionDateStr)
            cleanName
        } else {
            value
        }
        if (name.contains(tradedLabel)) {
            recentlyTraded = true
            name = name.remove(tradedLabel).trim()
        }
    }

    private fun CharacterBuilder.parseAccountBadges(rows: Elements) {
        val row = rows[0]
        for (column: Element in row.select("td")) {
            val popupSpan = column.selectFirst("span.HelperDivIndicator") ?: return
            val (title: String, popupContent: Document) = parsePopup(popupSpan.attr("onmouseover"))
            val description = popupContent.text()
            val imageUrl = column.selectFirst("img")?.attr("src")
            addBadge(title, description, imageUrl ?: continue)
        }
    }

    private fun CharacterBuilder.parseAccountAchievements(rows: Elements) {
        for (row: Element in rows) {
            val columns = row.select("td")
            if (columns.size != 2) continue
            val (gradeColumn, nameColumn) = columns
            val grade = gradeColumn.select("img").size
            val name = nameColumn.text()
            val secret = nameColumn.selectFirst("img") != null
            addAchievement(name, grade, secret)
        }
    }

    private fun CharacterBuilder.parseAccountInformation(rows: Elements) {
        val valueMap = mutableMapOf<String, String>()
        for (row: Element in rows) {
            val columns = row.select("td")
            var (field, value) = columns.map { it.wholeText().clean() }
            field = field.replace(" ", "_").remove(":").lowercase()
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
        accountInformation(created = parseTibiaDateTime(valueMap["created"] ?: return),
            loyaltyTitle = valueMap["loyalty_title"],
            position = valueMap["position"],
            tutorStars = valueMap["stars"]?.toInt())
    }

    private fun CharacterBuilder.parseCharacterDeaths(rows: Elements) {
        for (row: Element in rows) {
            val columns = row.select("td")
            if (columns.size != 2) continue
            val (dateColumn, descriptionColumn) = columns
            val deathDateTime: Instant = parseTibiaDateTime(dateColumn.text())
            val deathMatch = deathsRegex.find(descriptionColumn.toString())
            var (_, levelStr, killersDesc) = deathMatch?.groupValues ?: Triple("",
                "0",
                descriptionColumn.toString()).toList()
            var assistNameList: List<String> = mutableListOf()
            deathAssistsRegex.find(killersDesc)?.apply {
                killersDesc = this.groups["killers"]?.value ?: ""
                val assistsDec = this.groups["assists"]?.value ?: return
                assistNameList = linkSearch.findAll(assistsDec).map { it.value }.toList()
            }
            val killerNameList = killersDesc.splitList()
            val killerList = killerNameList.mapNotNull { parseKiller(it) }
            val assistsList = assistNameList.mapNotNull { parseKiller(it) }
            addDeath(deathDateTime, levelStr.toInt(), killerList, assistsList)
        }
    }

    private fun parseKiller(killerHtml: String): Killer? {
        var name: String = killerHtml
        var player = false
        var traded = false
        var summon: String? = null
        if (killerHtml.contains(tradedLabel)) {
            name = killerHtml.clean().remove(tradedLabel).trim()
            traded = true
            player = true
        }
        if (killerHtml.contains("href")) {
            name = linkContent.find(killerHtml)?.groups?.get(1)?.value ?: return null
            player = true
        }
        deathSummon.find(name)?.apply {
            summon = groups["summon"]!!.value.clean()
            name = groups["name"]!!.value.clean()
        }

        return Killer(name.clean(), player, summon, traded)
    }

    private fun CharacterBuilder.parseCharacters(rows: Elements) {
        for (row: Element in rows.subList(1, rows.size)) {
            val columns = row.select("td")
            val (nameColumn, worldColumn, statusColumn, _) = columns
            var traded = false
            var name = nameColumn.text().splitList(".").last().clean()
            if (name.contains(tradedLabel, true)) {
                name = name.remove(tradedLabel).trim()
                traded = true
            }
            val main = nameColumn.selectFirst("img") != null
            val world = worldColumn.text().clean()
            val status = statusColumn.text().clean()
            val online = status.contains("online")
            val deleted = status.contains("deleted")
            val position = if (status.contains("CipSoft Member")) "CipSoft Member" else null
            addCharacter(name, world, main, online, deleted, traded, position)
        }
    }
}
