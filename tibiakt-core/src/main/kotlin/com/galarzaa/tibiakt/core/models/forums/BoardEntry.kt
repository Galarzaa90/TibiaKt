package com.galarzaa.tibiakt.core.models.forums

import kotlinx.serialization.Serializable

@Serializable
data class BoardEntry(
    val name: String,
    val boardId: Int,
    val description: String,
    val posts: Int,
    val threads: Int,
    val lastPost: LastPost?,
)