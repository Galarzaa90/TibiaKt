@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.models.Paginated
import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import com.galarzaa.tibiakt.core.utils.getForumBoardUrl
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class ForumBoard(
    val name: String,
    override val boardId: Int,
    val sectionId: Int,
    val section: String,
    val threadAge: Int,
    val announcements: List<AnnouncementEntry>,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<ThreadEntry>,
) : Paginated<ThreadEntry>, BaseForumBoard {

    override val url get() = getForumBoardUrl(boardId, currentPage)
}

