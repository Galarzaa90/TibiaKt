/*
 * Copyright © 2024 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.galarzaa.tibiakt.core.models.character

import com.galarzaa.tibiakt.core.enums.Sex
import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.utils.getCharacterUrl
import kotlin.time.Instant
import kotlinx.serialization.Serializable

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
 * @property lastLogin The date when the character logged in the last time. If null, the character has never logged in.
 * @property position The special position the character holds.
 * @property comment The character's comment.
 * @property isPremium Whether the character has a premium account.
 * @property isRecentlyTraded Whether the character was recently traded. If its name was changed afterward, this flag is removed.
 * @property deletionDate The date when this character is scheduled to be deleted.
 * @property badges The visible badges of the character.
 * @property achievements The visible achievements for the character.
 * @property deaths The recent deaths of the character.
 * @property accountInformation The character's account information. Might be [isHidden].
 * @property otherCharacters The list of visible characters in the same account. Might be [isHidden].
 */
@Serializable
public data class Character(
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
    val isPremium: Boolean,
    val isRecentlyTraded: Boolean,
    val deletionDate: Instant?,
    val badges: List<AccountBadge>,
    val achievements: List<DisplayedAchievement>,
    val deaths: List<Death>,
    val accountInformation: AccountInformation?,
    val otherCharacters: List<OtherCharacter>,
) : BaseCharacter, CharacterLevel {

    /**
     * Whether this character is scheduled for deletion or nto.
     */
    val isScheduledForDeletion: Boolean get() = deletionDate != null

    /**
     * Whether this character is hidden or not.
     */
    val isHidden: Boolean get() = otherCharacters.isEmpty()

    /**
     * URL to the character this character is married to, if any.
     */
    val marriedToUrl: String? get() = marriedTo?.let { getCharacterUrl(it) }
}
