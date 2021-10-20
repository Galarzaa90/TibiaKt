package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.character.*
import java.time.Instant
import java.time.LocalDate

class CharacterBuilder {
    private var name: String? = null
    private var level: Int = 2
    private var residence: String? = null
    private var vocation: Vocation? = null
    private var sex: String? = null
    private var world: String? = null
    private var achievementPoints: Int = 0
    private var lastLogin: Instant? = null
    private var recentlyTraded: Boolean = false
    private var formerNames: List<String> = listOf()
    private var deletionDate: Instant? = null
    private var formerWorld: String? = null
    private var accountStatus: String? = null
    private var comment: String? = null
    private var title: String? = null
    private var position: String? = null
    private var unlockedTitles: Int = 0
    private var marriedTo: String? = null
    private val houses: MutableList<CharacterHouse> = mutableListOf()
    private var guildMembership: GuildMembership? = null
    private val accountBadges: MutableList<AccountBadge> = mutableListOf()
    private val achievements: MutableList<DisplayedAchievement> = mutableListOf()
    private var accountInformation: AccountInformation? = null
    private val deaths: MutableList<Death> = mutableListOf()
    private val characters: MutableList<OtherCharacter> = mutableListOf()

    fun name(name: String) = apply { this.name = name }
    fun titles(currentTitle: String?, unlockedTitles: Int) = apply {
        title = currentTitle
        this.unlockedTitles = unlockedTitles
    }

    fun vocation(vocation: Vocation) = apply { this.vocation = vocation }
    fun level(level: Int) = apply { this.level = level }
    fun sex(sex: String) = apply { this.sex = sex }
    fun world(world: String) = apply { this.world = world }
    fun achievementPoints(achievementPoints: Int) = apply { this.achievementPoints = achievementPoints }
    fun residence(residence: String) = apply { this.residence = residence }
    fun recentlyTraded(recentlyTraded: Boolean) = apply { this.recentlyTraded = recentlyTraded }
    fun lastLogin(lastLogin: Instant?) = apply { this.lastLogin = lastLogin }
    fun deletionDate(deletionDate: Instant?) = apply { this.deletionDate = deletionDate }
    fun formerNames(formerNames: List<String>) = apply { this.formerNames = formerNames }
    fun formerWorld(formerWorld: String?) = apply { this.formerWorld = formerWorld }
    fun position(position: String?) = apply { this.position = position }
    fun marriedTo(marriedTo: String?) = apply { this.marriedTo = marriedTo }
    fun accountStatus(accountStatus: String) = apply { this.accountStatus = accountStatus }
    fun comment(comment: String?) = apply { this.comment = comment }
    fun addHouse(name: String, houseId: Int, town: String, paidUntil: LocalDate, world: String) = apply {
        houses.add(CharacterHouse(name, houseId, town, paidUntil, world))
    }

    fun addBadge(name: String, descroption: String, iconUrl: String) = apply {
        accountBadges.add(AccountBadge(name, descroption, iconUrl))
    }

    fun addAchievement(name: String, grade: Int, secret: Boolean) = apply {
        achievements.add(DisplayedAchievement(name, grade, secret))
    }

    fun accountInformation(created: Instant, loyaltyTitle: String?, position: String?, tutorStars: Int?) = apply {
        accountInformation = AccountInformation(created, loyaltyTitle, position, tutorStars)
    }

    fun guild(rank: String, guild: String) = apply { guildMembership = GuildMembership(guild, rank) }

    fun addDeath(timestamp: Instant, level: Int, killers: List<Killer>, assists: List<Killer>) = apply {
        deaths.add(Death(timestamp, level, killers, assists))
    }

    fun addCharacter(
        name: String,
        world: String,
        main: Boolean = false,
        online: Boolean = false,
        deleted: Boolean = false,
        traded: Boolean = false,
        position: String?
    ) =
        apply { characters.add(OtherCharacter(name, world, main, online, deleted, traded, position)) }


    fun build() =
        Character(
            name = name ?: throw IllegalStateException("name is required"),
            title = title,
            formerNames = formerNames,
            unlockedTitles = unlockedTitles,
            sex = sex ?: throw IllegalStateException("sex is required"),
            vocation = vocation ?: throw IllegalStateException("vocation is required"),
            level = level,
            achievementPoints = achievementPoints,
            world = world ?: throw IllegalStateException("world is required"),
            formerWorld = formerWorld,
            residence = residence ?: throw IllegalStateException("residence is required"),
            marriedTo = marriedTo,
            houses = houses,
            guildMembership = guildMembership,
            lastLogin = lastLogin,
            position = position,
            comment = comment,
            accountStatus = accountStatus ?: throw IllegalStateException("accountStatus is required"),
            recentlyTraded = recentlyTraded,
            deletionDate = deletionDate,
            badges = accountBadges,
            achievements = achievements,
            deaths = deaths,
            accountInformation = accountInformation,
            characters = characters
        )
}