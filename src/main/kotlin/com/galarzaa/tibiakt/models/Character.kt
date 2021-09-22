@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.models

import com.galarzaa.tibiakt.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant


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


