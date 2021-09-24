package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.ParsingException
import com.galarzaa.tibiakt.builders.CharacterBuilder
import com.galarzaa.tibiakt.core.getLinkInformation
import com.galarzaa.tibiakt.core.parsePopup
import com.galarzaa.tibiakt.core.parseTibiaDate
import com.galarzaa.tibiakt.core.parseTibiaDateTime
import com.galarzaa.tibiakt.models.Character
import com.galarzaa.tibiakt.models.Killer
import com.galarzaa.tibiakt.utils.clean
import com.galarzaa.tibiakt.utils.parseTables
import com.galarzaa.tibiakt.utils.remove
import com.galarzaa.tibiakt.utils.splitList
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.time.Instant


object CharacterParser : Parser<Character?> {
    private val deletedRegexp = Regex("""([^,]+), will be deleted at (.*)""")
    private val titlesRegexp = Regex("""(.*)\((\d+) titles? unlocked\)""")
    private val houseRegexp = Regex("""\(([^)]+)\) is paid until (.*)""")
    private val deathsRegex = Regex("""Level (\d+) by (.*)\.</td>""")
    private val deathAssistsRegex = Regex("""(?<killers>.+)\.<br\s?/>Assisted by (?<assists>.+)""")
    private val linkSearch = Regex("""<a[^>]+>[^<]+</a>""")
    private val linkContent = Regex(""">([^<]+)<""")
    private val deathSummon = Regex("""(?<summon>an? .+) of (?<name>.*)""")
    private const val tradedLabel = "(traded)"

    override fun fromContent(content: String): Character? {
        val document: Document = Jsoup.parse(content, "", org.jsoup.parser.Parser.xmlParser())
        val boxContent =
            document.selectFirst("div.BoxContent") ?: throw ParsingException("BoxContent container not found")
        val tables = boxContent.parseTables()
        if (tables.keys.any { it.startsWith("Could not find character") })
            return null
        val builder = CharacterBuilder()
        parseCharacterInformation(tables["Character Information"] ?: return null, builder)
        tables["Account Badges"]?.apply { parseAccountBadges(this, builder) }
        tables["Account Achievements"]?.apply { parseAccountAchievements(this, builder) }
        tables["Account Information"]?.apply { parseAccountInformation(this, builder) }
        tables["Character Deaths"]?.apply { parseCharacterDeaths(this, builder) }
        tables["Characters"]?.apply { parseCharacters(this, builder) }
        return builder.build()
    }

    private fun parseCharacterInformation(rows: Elements, charBuilder: CharacterBuilder): CharacterBuilder {
        for (row: Element in rows) {
            val columns = row.select("td")
            var (field, value) = columns.map { it.wholeText().clean() }
            field = field.replace(" ", "_").remove(":").lowercase()
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
                "married_to" -> charBuilder.marriedTo(value)
                "house" -> parseHouseColumn(charBuilder, columns[1])
                "guild_membership" -> parseGuildColumn(charBuilder, columns[1])
            }
        }
        return charBuilder
    }

    private fun parseGuildColumn(charBuilder: CharacterBuilder, valueColumn: Element) {
        val guildName = valueColumn.selectFirst("a")?.text() ?: return
        val rankName = valueColumn.text().split("of the").first().trim()
        charBuilder.guild(rankName, guildName)
    }

    private fun parseHouseColumn(charBuilder: CharacterBuilder, valueColumn: Element) {
        val match = houseRegexp.find(valueColumn.ownText()) ?: return
        val (_, town, paidUntilStr) = match.groupValues
        val link = valueColumn.selectFirst("a")?.getLinkInformation() ?: return
        charBuilder.addHouse(
            link.title,
            link.queryParams["houseid"]?.first()?.toInt() ?: return,
            town,
            parseTibiaDate(paidUntilStr),
            world = link.queryParams["world"]?.first() ?: return
        )
    }

    private fun parseTitles(charBuilder: CharacterBuilder, value: String) {
        val match = titlesRegexp.find(value) ?: return
        val (_, currentTitle, unlockedTitles) = match.groupValues
        charBuilder.titles(
            if (currentTitle.contains("none", true)) null else currentTitle.trim(),
            unlockedTitles.toInt()
        )
    }

    private fun parseNameField(charBuilder: CharacterBuilder, value: String) {
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
            name = name.remove(tradedLabel).trim()
        }
        charBuilder.name(name)
    }

    private fun parseAccountBadges(rows: Elements, builder: CharacterBuilder) {
        val row = rows[0]
        for (column: Element in row.select("td")) {
            val popupSpan = column.select("span.HelperDivIndicator") ?: return
            val (title: String, popupContent: Document) = parsePopup(popupSpan.attr("onmouseover"))
            val description = popupContent.text()
            val imageUrl = column.selectFirst("img")?.attr("src")
            builder.addBadge(title, description, imageUrl ?: continue)
        }
    }

    private fun parseAccountAchievements(rows: Elements, builder: CharacterBuilder) {
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

    private fun parseAccountInformation(rows: Elements, builder: CharacterBuilder) {
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
        builder.accountInformation(
            created = parseTibiaDateTime(valueMap["created"] ?: return),
            loyaltyTitle = valueMap["loyalty_title"],
            position = valueMap["position"],
            tutorStars = valueMap["stars"]?.toInt()
        )
    }

    private fun parseCharacterDeaths(rows: Elements, builder: CharacterBuilder) {
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
            val assistsList = assistNameList.mapNotNull { parseKiller(it) }
            builder.addDeath(deathDateTime, levelStr.toInt(), killerList, assistsList)
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

        return Killer(name, player, summon, traded)
    }

    private fun parseCharacters(rows: Elements, builder: CharacterBuilder) {
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
            builder.addCharacter(name, world, main, online, deleted, traded, position)
        }
    }
}
