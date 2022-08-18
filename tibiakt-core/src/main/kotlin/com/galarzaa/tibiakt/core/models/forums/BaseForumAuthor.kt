/*
 * Copyright © 2022 Allan Galarza
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

package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.character.BaseCharacter
import com.galarzaa.tibiakt.core.models.character.CharacterLevel
import com.galarzaa.tibiakt.core.models.character.GuildMembershipWithTitle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Base interface for forum author classes */
@Serializable
sealed class BaseForumAuthor {
    abstract val name: String
}

/**
 * A forum author that is no longer available due to being deleted or having been traded.
 *
 * If the author [isTraded], it means that the post was made by the account that owned the character before.
 */
@Serializable
@SerialName("unavailableForumAuthor")
data class UnavailableForumAuthor(
    override val name: String,
    val isDeleted: Boolean,
    val isTraded: Boolean,
) : BaseForumAuthor()

/**
 * The author of a forum post or thread.
 *
 * If the character [isRecentlyTraded], it means that the character was traded in the last 30 days, but the post was made by the new owner.
 *
 * @property name The name of the author.
 * @property level The level of the author.
 * @property world The current world of the author.
 * @property position The official position of the author, if any.
 * @property title The selected title of the character, if any.
 * @property guild The guild of the character, if any.
 * @property posts The total number of posts by this author.
 * @property isRecentlyTraded The character was traded in the last 30 days.
 */
@SerialName("forumAuthor")
@Serializable
data class ForumAuthor(
    override val name: String,
    override val level: Int,
    val world: String,
    val position: String?,
    val title: String?,
    val vocation: Vocation,
    val guild: GuildMembershipWithTitle?,
    val posts: Int,
    val isRecentlyTraded: Boolean,
) : BaseForumAuthor(), BaseCharacter, CharacterLevel


@SerialName("tournamentForumAuthor")
@Serializable
data class TournamentForumAuthor(
    override val name: String,
    val posts: Int,
) : BaseForumAuthor(), BaseCharacter