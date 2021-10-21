package com.galarzaa.tibiakt.core.models.news

import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.utils.getNewsUrl

/**
 * @property id The internal ID of the news entry.
 * @property category The category of the entry.
 */
interface BaseNews {
    val id: Int
    val category: NewsCategory

    /**
     * The URL to the article.
     */
    val url
        get() = getNewsUrl(id)
}