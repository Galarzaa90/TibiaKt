@file:UseSerializers(InstantSerializer::class, LocalDateSerializer::class)

package com.galarzaa.tibiakt.models

import com.galarzaa.tibiakt.InstantSerializer
import com.galarzaa.tibiakt.LocalDateSerializer
import com.galarzaa.tibiakt.core.getCharacterUrl
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant
import java.time.LocalDate
import kotlin.math.ceil
import kotlin.math.floor


@Serializable
data class Character(
    val name: String,
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
)

val Character.shareRange: IntRange
    get() {
        val minLevel = floor((level / 3.0) * 2).toInt()
        val maxLevel = ceil((level / 2.0) * 3).toInt() + if (level % 2 == 0) 1 else 0
        return minLevel..maxLevel
    }
val Character.url: String
    get() = getCharacterUrl(name)
val Character.scheduledForDeletion: Boolean
    get() = deletionDate != null

fun Character.Companion.getUrl(name: String) = getCharacterUrl(name)

@Serializable
data class OtherCharacter(
    val name: String,
    val world: String,
    val main: Boolean = false,
    val isOnline: Boolean = false,
    val isDeleted: Boolean = false,
    val recentlyTraded: Boolean = false,
    val position: String?
)

@Serializable
data class CharacterHouse(
    val name: String,
    val houseId: Int,
    val town: String,
    val paidUntil: LocalDate
)

@Serializable
data class GuildMembership(val guildName: String, val guildRank: String)

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
 * @property recentlyTraded Whether the character was traded after this death occurred.
 * @property summon The summoned creature that caused this death, if applicable.
 */
@Serializable
data class Killer(
    val name: String,
    val isPlayer: Boolean,
    val summon: String? = null,
    val recentlyTraded: Boolean = false
)