package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.AccountStatus
import com.galarzaa.tibiakt.core.enums.Sex
import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.character.AccountBadge
import com.galarzaa.tibiakt.core.models.character.AccountInformation
import com.galarzaa.tibiakt.core.models.character.Character
import com.galarzaa.tibiakt.core.models.character.CharacterHouse
import com.galarzaa.tibiakt.core.models.character.Death
import com.galarzaa.tibiakt.core.models.character.DisplayedAchievement
import com.galarzaa.tibiakt.core.models.character.GuildMembership
import com.galarzaa.tibiakt.core.models.character.Killer
import com.galarzaa.tibiakt.core.models.character.OtherCharacter
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import kotlinx.datetime.Instant
import java.time.LocalDate

@BuilderDsl
inline fun character(block: CharacterBuilder.() -> Unit) = CharacterBuilder().apply(block).build()

@BuilderDsl
inline fun characterBuilder(block: CharacterBuilder.() -> Unit) = CharacterBuilder().apply(block)

@BuilderDsl
class CharacterBuilder : TibiaKtBuilder<Character>() {
    lateinit var name: String
    var level: Int = 2
    var residence: String? = null
    var vocation: Vocation? = null
    var sex: Sex? = null
    var world: String? = null
    var achievementPoints: Int = 0
    var lastLogin: Instant? = null
    var recentlyTraded: Boolean = false
    var formerNames: List<String> = listOf()
    var deletionDate: Instant? = null
    var formerWorld: String? = null
    var accountStatus: AccountStatus? = null
    var comment: String? = null
    var title: String? = null
    var position: String? = null
    var unlockedTitles: Int = 0
    var marriedTo: String? = null
    val houses: MutableList<CharacterHouse> = mutableListOf()
    var guildMembership: GuildMembership? = null
    val accountBadges: MutableList<AccountBadge> = mutableListOf()
    val achievements: MutableList<DisplayedAchievement> = mutableListOf()
    var accountInformation: AccountInformation? = null
    val deaths: MutableList<Death> = mutableListOf()
    val characters: MutableList<OtherCharacter> = mutableListOf()

    fun addHouse(name: String, houseId: Int, town: String, paidUntil: LocalDate, world: String) = apply {
        houses.add(CharacterHouse(name, houseId, town, paidUntil, world))
    }

    @BuilderDsl
    fun house(block: CharacterHouseBuilder.() -> Unit) = houses.add(CharacterHouseBuilder().apply(block).build())

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
        position: String?,
    ) = apply { characters.add(OtherCharacter(name, world, main, online, deleted, traded, position)) }

    override fun build() =
        Character(name = if (::name.isInitialized) name else throw IllegalStateException("name is required"),
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
            characters = characters)

    class CharacterHouseBuilder : TibiaKtBuilder<CharacterHouse>() {
        lateinit var name: String
        var houseId: Int = 0
        lateinit var town: String
        lateinit var paidUntil: LocalDate
        lateinit var world: String
        override fun build() = CharacterHouse(
            name = if (::name.isInitialized) name else throw IllegalStateException("name is required"),
            houseId = houseId,
            town = if (::town.isInitialized) town else throw IllegalStateException("town is required"),
            paidUntil = if (::paidUntil.isInitialized) paidUntil else throw IllegalStateException("paidUntil is required"),
            world = if (::world.isInitialized) world else throw IllegalStateException("world is required"),
        )
    }
}