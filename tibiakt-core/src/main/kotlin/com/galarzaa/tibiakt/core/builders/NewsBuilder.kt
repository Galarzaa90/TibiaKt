package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.models.news.News
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.LocalDate

@BuilderDsl
public inline fun newsBuilder(block: NewsBuilder.() -> Unit): NewsBuilder = NewsBuilder().apply(block)

@BuilderDsl
public inline fun news(block: NewsBuilder.() -> Unit): News = newsBuilder(block).build()

@BuilderDsl
public class NewsBuilder : TibiaKtBuilder<News>() {
    public var id: Int? = null
    public lateinit var title: String
    public lateinit var category: NewsCategory
    public lateinit var date: LocalDate
    public lateinit var content: String
    public var threadId: Int? = null
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
