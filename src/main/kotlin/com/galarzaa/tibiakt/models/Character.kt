@file:UseSerializers(InstantSerializer::class, LocalDateSerializer::class)

package com.galarzaa.tibiakt.models

import com.galarzaa.tibiakt.InstantSerializer
import com.galarzaa.tibiakt.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant
import java.time.LocalDate


/**
 * Represents the character information available on Tibia.com.
 * @property recentlyTraded Whether the character was recently traded. If its name was changed afterwards, this flag is removed.
 */
@Serializable
data class Character(
    override val name: String,
    val level: Int,
    val sex: String,
    val vocation: String,
    val world: String,
    val achievementPoints: Int = 0,
    val residence: String,
    val accountStatus: String,
    val recentlyTraded: Boolean = false,
    val deletionDate: Instant? = null,
    val title: String? = null,
    val unlockedTitles: Int = 0,
    val formerNames: List<String> = emptyList(),
    val formerWorld: String? = null,
    val marriedTo: String? = null,
    val houses: List<CharacterHouse> = emptyList(),
    val guildMembership: GuildMembership? = null,
    val lastLogin: Instant? = null,
    val position: String? = null,
    val comment: String? = null,
    val badges: List<AccountBadge> = emptyList(),
    val achievements: List<DisplayedAchievement> = emptyList(),
    val deaths: List<Death> = emptyList(),
    val accountInformation: AccountInformation? = null,
    val characters: List<OtherCharacter> = emptyList(),
) : BaseCharacter

@Serializable
data class OtherCharacter(
    override val name: String,
    val world: String,
    val main: Boolean = false,
    val isOnline: Boolean = false,
    val isDeleted: Boolean = false,
    val recentlyTraded: Boolean = false,
    val position: String?
) : BaseCharacter


@Serializable
data class CharacterHouse(
    val name: String,
    val houseId: Int,
    val town: String,
    val paidUntil: LocalDate,
    val world: String,
)

@Serializable
data class GuildMembership(val guildRank: String, val guildName: String)

@Serializable
data class DisplayedAchievement(val name: String, val grade: Int, val secret: Boolean = false)

@Serializable
data class AccountBadge(val name: String, val description: String, val imageUrl: String)

@Serializable
data class AccountInformation(
    val creation: Instant,
    val loyaltyTitle: String?,
    val position: String? = null,
    val tutorStars: Int? = null
)

@Serializable
data class Death(val timestamp: Instant, val level: Int, val killers: List<Killer>, val assists: List<Killer>)

/**
 * Represents a killer listed in a death.
 *
 * @property name The name of the killer. If the killer is a summoned creature, this is the summoner's name.
 * @property isPlayer Whether the killer is a player.
 * @property traded Whether the character was traded after this death occurred.
 * @property summon The summoned creature that caused this death, if applicable.
 */
@Serializable
data class Killer(
    val name: String,
    val isPlayer: Boolean,
    val summon: String? = null,
    val traded: Boolean = false
)