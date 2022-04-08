package com.galarzaa.tibiakt.core.models.forums

import kotlinx.serialization.Serializable


@Serializable
data class AnnouncementEntry(
    val title: String,
    val announcementId: Int,
    val author: String,
)

