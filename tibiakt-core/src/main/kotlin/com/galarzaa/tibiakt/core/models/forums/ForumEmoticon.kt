package com.galarzaa.tibiakt.core.models.forums

import kotlinx.serialization.Serializable

@Serializable
data class ForumEmoticon(
    val name: String,
    val url: String,
)