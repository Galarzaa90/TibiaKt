package com.galarzaa.tibiakt.core.models.bazaar

import com.galarzaa.tibiakt.core.utils.getStaticFileUrl
import kotlinx.serialization.Serializable

/**
 * A special highlight or description of the auction, selected by the author
 *
 * @property content The content of the argument.
 * @property categoryId The id of the category of the argument.
 */
@Serializable
data class SalesArgument(
    val categoryId: Int,
    val content: String,
) {
    /**
     * The URL to the image corresponding to the category.
     */
    val categoryImageUrl get() = getStaticFileUrl("images", "charactertrade", "usp-category-$categoryId.png")
}