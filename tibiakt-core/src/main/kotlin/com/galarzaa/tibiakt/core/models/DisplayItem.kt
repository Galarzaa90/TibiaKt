package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.getStaticFileUrl
import kotlinx.serialization.Serializable

@Serializable
data class DisplayItem(
    val itemId: Int,
    val name: String,
    val description: String?,
    val count: Int,
) {
    val imageUrl get() = getStaticFileUrl("images", "charactertrade", "objects", "$itemId.gif")
}