@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import com.galarzaa.tibiakt.core.utils.getCharacterUrl
import com.galarzaa.tibiakt.core.utils.getForumPostUrl
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

/**
 * Information about the last post in a board or thread.
 *
 * @property author The name of the character that made the last post.
 * @property postId The internal ID of the last post.
 * @property date The date and time when the post was published.
 * @property deleted Whether the author of the post is currently deleted.
 * @property traded Whether the post was made by a character that was traded afterwards.
 */
@Serializable
data class LastPost(
    val author: String,
    val postId: Int,
    val date: Instant,
    val deleted: Boolean,
    val traded: Boolean,
) {
    /** The URL to the author's character page. If the author is deleted, there is no URL. */
    val authorUrl get() = if(!deleted) getCharacterUrl(author) else null
    val postUrl get() = getForumPostUrl(postId)
}