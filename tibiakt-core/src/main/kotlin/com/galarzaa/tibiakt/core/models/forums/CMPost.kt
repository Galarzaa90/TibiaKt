@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import com.galarzaa.tibiakt.core.utils.getForumPostUrl
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

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
    val postId: Int,
    val date: Instant,
    val board: String,
    val threadTitle: String,
) {
    val postUrl: String get() = getForumPostUrl(postId)
}
