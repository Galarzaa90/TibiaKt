package com.galarzaa.tibiakt.builders

import com.galarzaa.tibiakt.models.News
import com.galarzaa.tibiakt.models.NewsCategory
import java.time.LocalDate

class NewsBuilder {
    private var id: Int? = null
    private var title: String? = null
    private var category: NewsCategory? = null
    private var categoryIcon: String? = null
    private var date: LocalDate? = null
    private var content: String? = null
    private var threadId: Int? = null

    fun id(id: Int) = apply { this.id = id }
    fun title(title: String) = apply { this.title = title }
    fun category(icon: String) = apply { categoryIcon = icon; category = NewsCategory.fromIcon(icon) }
    fun date(date: LocalDate) = apply { this.date = date }
    fun content(content: String) = apply { this.content = content }
    fun threadId(threadId: Int?) = apply { this.threadId = threadId }

    fun build(): News {
        return News(
            id = id ?: throw IllegalStateException("id is required"),
            title = title ?: throw IllegalStateException("title is required"),
            category = category ?: throw IllegalStateException("category is required"),
            categoryIcon = categoryIcon ?: throw IllegalStateException("categoryIcon is required"),
            date = date ?: throw IllegalStateException("date is required"),
            content = content ?: throw IllegalStateException("content is required"),
            threadId = threadId
        )
    }
}