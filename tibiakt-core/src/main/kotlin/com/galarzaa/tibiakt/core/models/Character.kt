@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.InstantSerializer
import com.galarzaa.tibiakt.core.enums.Vocation
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
    val title: String? = null,
    val formerNames: List<String> = emptyList(),
    val unlockedTitles: Int = 0,
    val sex: String,
    val vocation: Vocation,
    val level: Int,
    val achievementPoints: Int = 0,
    val world: String,
    val formerWorld: String? = null,
    val residence: String,
    val marriedTo: String? = null,
    val houses: List<CharacterHouse> = emptyList(),
    val guildMembership: GuildMembership? = null,
    val lastLogin: Instant? = null,
    val position: String? = null,
    val comment: String? = null,
    val accountStatus: String,
    val recentlyTraded: Boolean = false,
    val deletionDate: Instant? = null,
    val badges: List<AccountBadge> = emptyList(),
    val achievements: List<DisplayedAchievement> = emptyList(),
    val deaths: List<Death> = emptyList(),
    val accountInformation: AccountInformation? = null,
    val characters: List<OtherCharacter> = emptyList(),
) : BaseCharacter


