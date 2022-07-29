@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

/**
 * A post made by a Community Manager in the [CMPostArchive]
 *
 * @property postId The ID of the post.
 * @property date The date and time when the post was created.
 * @property board The name of the board where the post is.
 * @property threadTitle The title of the thread where the post is.
 */
@Serializable
data class CMPost(
    override val postId: Int,
    val date: Instant,
    val board: String,
    val threadTitle: String,
) : BaseForumPost
