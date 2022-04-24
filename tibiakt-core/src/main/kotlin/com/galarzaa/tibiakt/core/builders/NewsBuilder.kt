package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.models.news.News
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.LocalDate

@BuilderDsl
inline fun newsBuilder(block: NewsBuilder.() -> Unit) = NewsBuilder().apply(block)

@BuilderDsl
inline fun news(block: NewsBuilder.() -> Unit) = newsBuilder(block).build()

@BuilderDsl
class NewsBuilder : TibiaKtBuilder<News>() {
    var id: Int? = null
    lateinit var title: String
    lateinit var category: NewsCategory
    lateinit var date: LocalDate
    lateinit var content: String
    var threadId: Int? = null
    override fun build(): News {
        return News(
            id = id ?: throw IllegalStateException("id is required"),
            title = if (::title.isInitialized) title else throw IllegalStateException("title is required"),
            category = if (::category.isInitialized) category else throw IllegalStateException("category is required"),
            date = if (::date.isInitialized) date else throw IllegalStateException("date is required"),
            content = if (::content.isInitialized) content else throw IllegalStateException("content is required"),
            threadId = threadId,
        )
    }
}