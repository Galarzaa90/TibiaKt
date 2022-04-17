package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.character.BaseCharacter
import com.galarzaa.tibiakt.core.models.character.GuildMembership
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
    val guild: GuildMembership?,
    val posts: Int,
    val isRecentlyTraded: Boolean,
) : BaseForumAuthor(), BaseCharacter


@SerialName("tournamentForumAuthor")
@Serializable
data class TournamentForumAuthor(
    override val name: String,
    val posts: Int,
) : BaseForumAuthor(), BaseCharacter