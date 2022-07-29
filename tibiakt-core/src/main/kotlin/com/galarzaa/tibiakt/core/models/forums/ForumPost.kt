@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

/** A post in a [ForumThread]
 *
 * @property author The author of the post.
 * @property emoticon The emoticon chosen for the post, if any.
 * @property title The title of the post, if any.
 * @property content The HTML content of the post.
 * @property signature The signature of the post's author.
 * @property postId The ID of the post.
 * @property postedDate The date when the post was created.
 * @property editedDate The date when the post was last edited. Null if it hasn't been edited.
 * @property editedBy The name of the character that last edited the post. CipSoft members might edit posts.
 */
@Serializable
data class ForumPost(
    val author: BaseForumAuthor,
    val emoticon: ForumEmoticon?,
    val title: String?,
    val content: String,
    val signature: String?,
    override val postId: Int,
    val postedDate: Instant,
    val editedDate: Instant?,
    val editedBy: String?,
) : BaseForumPost