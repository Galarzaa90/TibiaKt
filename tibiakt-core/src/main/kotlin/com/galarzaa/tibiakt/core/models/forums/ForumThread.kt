package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.models.Paginated
import kotlinx.serialization.Serializable

@Serializable
data class ForumThread(
    val title: String,
    val threadId: Int,
    val board: String,
    val boardId: Int,
    val section: String,
    val sectionId: Int,
    val previousTopicNumber: Int?,
    val nextTopicNumber: Int?,
    val goldenFrame: Boolean,
    val anchoredPost: ForumPost? = null,
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<ForumPost>,
): Paginated<ForumPost>
