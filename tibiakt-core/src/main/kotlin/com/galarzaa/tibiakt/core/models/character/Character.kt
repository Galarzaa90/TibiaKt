@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.character

import com.galarzaa.tibiakt.core.enums.AccountStatus
import com.galarzaa.tibiakt.core.enums.Sex
import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import com.galarzaa.tibiakt.core.utils.getCharacterUrl
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant


/**
 * Represents the character information available on Tibia.com.
 *
 * @property title The title selected to be displayed.
 * @property formerNames A list of former names the player had in the last 6 months.
 * @property unlockedTitles The amount of titles the player has unlocked in total.
 * @property sex The sex of the character.
 * @property vocation The vocation of the character.
 * @property level The level of the character.
 * @property achievementPoints The total achievement points.
 * @property world The current world of the character.
 * @property formerWorld The former world of the character. Only visible for 6 months.
 * @property residence The current city where the character will respawn on death.
 * @property marriedTo The name of the character this character is married to, if any.
 * @property houses The list of houses currently owned by the character.
 * @property guildMembership The guild the character belongs to.
 * @property position The special position the character holds.
 * @property comment The character's comment.
 * @property accountStatus The current status of the account.
 * @property recentlyTraded Whether the character was recently traded. If its name was changed afterwards, this flag is removed.
 * @property deletionDate The date when this character is scheduled to be deleted.
 * @property badges The visible badges of the character.
 * @property achievements The visible achievements for the character.
 * @property deaths The recent deaths of the character.
 * @property accountInformation The character's account information. Might be [hidden].
 * @property characters The list of visible characters in the same account. Might be [hidden].
 */
@Serializable
data class Character(
    override val name: String,
    val title: String?,
    val formerNames: List<String>,
    val unlockedTitles: Int,
    val sex: Sex,
    val vocation: Vocation,
    override val level: Int,
    val achievementPoints: Int,
    val world: String,
    val formerWorld: String?,
    val residence: String,
    val marriedTo: String?,
    val houses: List<CharacterHouse>,
    val guildMembership: GuildMembership?,
    val lastLogin: Instant?,
    val position: String?,
    val comment: String?,
    val accountStatus: AccountStatus,
    val recentlyTraded: Boolean,
    val deletionDate: Instant?,
    val badges: List<AccountBadge>,
    val achievements: List<DisplayedAchievement>,
    val deaths: List<Death>,
    val accountInformation: AccountInformation?,
    val characters: List<OtherCharacter>,
) : BaseCharacter, CharacterLevel {

    /**
     * Whether this character is scheduled for deletion or nto.
     */
    val scheduledForDeletion: Boolean get() = deletionDate != null

    /**
     * Whether this character is hidden or not.
     */
    val hidden: Boolean get() = characters.isEmpty()

    /**
     * URL to the character this character is married to, if any.
     */
    val marriedToUrl: String? get() = marriedTo?.let { getCharacterUrl(it) }
}