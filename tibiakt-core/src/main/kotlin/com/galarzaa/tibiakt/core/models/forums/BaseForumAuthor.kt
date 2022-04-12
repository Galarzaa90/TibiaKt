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

@Serializable
@SerialName("unavailableForumAuthor")
data class UnavailableForumAuthor(
    override val name: String,
    val deleted: Boolean,
    val traded: Boolean,
) : BaseForumAuthor()

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
) : BaseForumAuthor(), BaseCharacter