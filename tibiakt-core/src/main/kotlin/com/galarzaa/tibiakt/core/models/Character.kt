@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.utils.InstantSerializer
import com.galarzaa.tibiakt.core.utils.getCharacterUrl
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant
import kotlin.math.ceil
import kotlin.math.floor


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
) : BaseCharacter {

    val shareRange: IntRange
        get() {
            val minLevel = floor((level / 3.0) * 2).toInt()
            val maxLevel = ceil((level / 2.0) * 3).toInt() + if (level % 2 == 0) 1 else 0
            return minLevel..maxLevel
        }

    /**
     * Whether this character is scheduled for deletion or nto.
     */
    val scheduledForDeletion: Boolean
        get() = deletionDate != null

    /**
     * Whether this character is hidden or not.
     */
    val hidden: Boolean
        get() = characters.isEmpty()

    /**
     * URL to the character this character is married to, if any.
     */
    val marriedToUrl: String?
        get() {
            return getCharacterUrl(marriedTo ?: return null)
        }
}