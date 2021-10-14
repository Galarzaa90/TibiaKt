package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.getNewsUrl

/**
 * @property id The internal ID of the news entry.
 */
interface BaseNews {
    val id: Int
}

/**
 * The URL to the article.
 */
val BaseNews.url
    get() = getNewsUrl(id)