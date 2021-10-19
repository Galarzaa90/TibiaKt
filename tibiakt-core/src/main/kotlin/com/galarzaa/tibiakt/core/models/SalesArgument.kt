package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.getStaticFileUrl
import kotlinx.serialization.Serializable

@Serializable
data class SalesArgument(
    val categoryId: Int,
    val content: String,
) {
    val categoryImageUrl get() = getStaticFileUrl("images/charactertrade/usp-category-$categoryId.png")
}