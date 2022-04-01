@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

@Serializable
data class LastPost(
    val author: String,
    val postId: Int,
    val date: Instant,
    val deleted: Boolean,
    val traded: Boolean,
)