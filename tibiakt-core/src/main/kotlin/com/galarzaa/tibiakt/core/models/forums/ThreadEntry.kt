package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.enums.ThreadStatus
import kotlinx.serialization.Serializable

@Serializable
data class ThreadEntry(
    val title: String,
    val threadId: Int,
    val author: String,
    val authorTraded: Boolean,
    val authorDeleted: Boolean,
    val replies: Int,
    val views: Int,
    val lastPost: LastPost,
    val status: Set<ThreadStatus>,
    val pages: Int,
    val goldenFrame: Boolean,
)