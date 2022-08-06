package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.character.BaseCharacter
import com.galarzaa.tibiakt.core.models.character.GuildMembershipWithTitle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    val level: Int,
    val world: String,
    val position: String?,
    val title: String?,
    val vocation: Vocation,
    val guild: GuildMembershipWithTitle?,
    val posts: Int,
    val isRecentlyTraded: Boolean,
) : BaseForumAuthor(), BaseCharacter


@SerialName("tournamentForumAuthor")
@Serializable
data class TournamentForumAuthor(
    override val name: String,
    val posts: Int,
) : BaseForumAuthor(), BaseCharacter