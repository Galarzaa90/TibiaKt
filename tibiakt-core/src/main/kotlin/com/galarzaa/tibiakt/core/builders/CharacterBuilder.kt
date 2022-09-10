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
public inline fun character(block: CharacterBuilder.() -> Unit): Character = CharacterBuilder().apply(block).build()

@BuilderDsl
public inline fun characterBuilder(block: CharacterBuilder.() -> Unit): CharacterBuilder =
    CharacterBuilder().apply(block)

@BuilderDsl
public class CharacterBuilder : TibiaKtBuilder<Character>() {
    public lateinit var name: String
    public var level: Int = 2
    public var residence: String? = null
    public var vocation: Vocation? = null
    public var sex: Sex? = null
    public var world: String? = null
    public var achievementPoints: Int = 0
    public var lastLogin: Instant? = null
    public var recentlyTraded: Boolean = false
    public var formerNames: List<String> = listOf()
    public var deletionDate: Instant? = null
    public var formerWorld: String? = null
    public var accountStatus: AccountStatus? = null
    public var comment: String? = null
    public var title: String? = null
    public var position: String? = null
    public var unlockedTitles: Int = 0
    public var marriedTo: String? = null
    public val houses: MutableList<CharacterHouse> = mutableListOf()
    public var guildMembership: GuildMembership? = null
    public val accountBadges: MutableList<AccountBadge> = mutableListOf()
    public val achievements: MutableList<DisplayedAchievement> = mutableListOf()
    public var accountInformation: AccountInformation? = null
    public val deaths: MutableList<Death> = mutableListOf()
    public val characters: MutableList<OtherCharacter> = mutableListOf()

    public fun addHouse(
        name: String,
        houseId: Int,
        town: String,
        paidUntil: LocalDate,
        world: String,
    ): CharacterBuilder = apply {
        houses.add(CharacterHouse(name, houseId, town, paidUntil, world))
    }

    @BuilderDsl
    public fun house(block: CharacterHouseBuilder.() -> Unit): CharacterBuilder =
        apply { houses.add(CharacterHouseBuilder().apply(block).build()) }

    public fun addBadge(name: String, descroption: String, iconUrl: String): CharacterBuilder = apply {
        accountBadges.add(AccountBadge(name, descroption, iconUrl))
    }

    public fun addAchievement(name: String, grade: Int, secret: Boolean): CharacterBuilder = apply {
        achievements.add(DisplayedAchievement(name, grade, secret))
    }

    public fun accountInformation(
        created: Instant,
        loyaltyTitle: String?,
        position: String?,
        tutorStars: Int?,
    ): CharacterBuilder = apply {
        accountInformation = AccountInformation(created, loyaltyTitle, position, tutorStars)
    }

    public fun guild(rank: String, guild: String): CharacterBuilder =
        apply { guildMembership = GuildMembership(guild, rank) }

    public fun addDeath(
        timestamp: Instant,
        level: Int,
        killers: List<Killer>,
        assists: List<Killer>,
    ): CharacterBuilder = apply {
        deaths.add(Death(timestamp, level, killers, assists))
    }

    public fun addCharacter(
        name: String,
        world: String,
        main: Boolean = false,
        online: Boolean = false,
        deleted: Boolean = false,
        traded: Boolean = false,
        position: String?,
    ): CharacterBuilder = apply { characters.add(OtherCharacter(name, world, main, online, deleted, traded, position)) }

    override fun build(): Character = Character(
        name = if (::name.isInitialized) name else throw IllegalStateException("name is required"),
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

    public class CharacterHouseBuilder : TibiaKtBuilder<CharacterHouse>() {
        public lateinit var name: String
        public var houseId: Int = 0
        public lateinit var town: String
        public lateinit var paidUntil: LocalDate
        public lateinit var world: String
        public override fun build(): CharacterHouse = CharacterHouse(
            name = if (::name.isInitialized) name else throw IllegalStateException("name is required"),
            houseId = houseId,
            town = if (::town.isInitialized) town else throw IllegalStateException("town is required"),
            paidUntil = if (::paidUntil.isInitialized) paidUntil else throw IllegalStateException("paidUntil is required"),
            world = if (::world.isInitialized) world else throw IllegalStateException("world is required"),
        )
    }
}
