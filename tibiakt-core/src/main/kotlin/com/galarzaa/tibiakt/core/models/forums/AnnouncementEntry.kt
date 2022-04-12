package com.galarzaa.tibiakt.core.models.forums

import kotlinx.serialization.Serializable


@Serializable
data class AnnouncementEntry(
    override val title: String,
    override val announcementId: Int,
    val author: String,
) : BaseForumAnnouncement

