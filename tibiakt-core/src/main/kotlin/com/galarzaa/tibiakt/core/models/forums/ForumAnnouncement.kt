@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

@Serializable
data class ForumAnnouncement(
    override val announcementId: Int,
    override val title: String,
    val board: String,
    val boardId: Int,
    val section: String,
    val sectionId: Int,
    val author: BaseForumAuthor,
    val content: String,
    val startDate: Instant,
    val endDate: Instant,
) : BaseForumAnnouncement
