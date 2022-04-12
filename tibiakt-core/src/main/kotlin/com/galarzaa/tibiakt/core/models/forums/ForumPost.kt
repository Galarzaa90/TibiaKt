@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

@Serializable
data class ForumPost(
    val author: ForumAuthor,
    val emoticon: ForumEmoticon?,
    val title: String?,
    val content: String,
    val signature: String?,
    val postId: Int,
    val postedDate: Instant,
    val editedDate: Instant?,
    val editedBy: String?,
)