package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.character.GuildMembership
import kotlinx.serialization.Serializable

@Serializable
sealed class ForumAuthor(

) {
    abstract val name: String
    abstract val deleted: Boolean
    abstract val traded: Boolean

    @Serializable
    data class Unavailable(
        override val name: String,
        override val deleted: Boolean,
        override val traded: Boolean,
    ) : ForumAuthor()

    @Serializable
    data class Available(
        override val name: String,
        override val deleted: Boolean,
        override val traded: Boolean,
        val level: Int,
        val world: String,
        val position: String?,
        val title: String?,
        val vocation: Vocation,
        val guild: GuildMembership?,
        val posts: Int,
    ) : ForumAuthor()


}