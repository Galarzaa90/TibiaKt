package com.galarzaa.tibiakt.core.models.forums

import kotlinx.serialization.Serializable

@Serializable
sealed class ForumEntry {
    abstract val title: String
    abstract val author: String
}

@Serializable
data class AnnouncementEntry(
    override val title: String,
    val announcementId: Int,
    override val author: String,
) : ForumEntry()

@Serializable
data class ThreadEntry(
    override val title: String,
    val threadId: Int,
    override val author: String,
    val authorTraded: Boolean,
    val replies: Int,
    val views: Int,
    val lastPost: LastPost,
    //val status: ThreadStatus,
    val pages: Int,
    val goldenFrame: Boolean,
) : ForumEntry()