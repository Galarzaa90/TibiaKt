package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.models.news.News
import java.time.LocalDate

inline fun newsBuilder(block: NewsBuilder.() -> Unit) = NewsBuilder().apply(block)
inline fun news(block: NewsBuilder.() -> Unit) = newsBuilder(block).build()

@TibiaKtDsl
class NewsBuilder {
    var id: Int? = null
    var title: String? = null
    var category: NewsCategory? = null
    var date: LocalDate? = null
    var content: String? = null
    var threadId: Int? = null

    fun build(): News {
        return News(
            id = id ?: throw IllegalStateException("id is required"),
            title = title ?: throw IllegalStateException("title is required"),
            category = category ?: throw IllegalStateException("category is required"),
            date = date ?: throw IllegalStateException("date is required"),
            content = content ?: throw IllegalStateException("content is required"),
            threadId = threadId
        )
    }
}