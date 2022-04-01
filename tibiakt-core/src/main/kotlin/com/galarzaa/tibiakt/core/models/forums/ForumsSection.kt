package com.galarzaa.tibiakt.core.models.forums

import kotlinx.serialization.Serializable

@Serializable
data class ForumsSection(
    val sectionId: Int,
    val entries: List<BoardEntry>,
)