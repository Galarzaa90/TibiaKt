/*
 * Copyright © 2025 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.galarzaa.tibiakt.core.section.news.article.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.builder.requireField
import com.galarzaa.tibiakt.core.section.news.article.model.NewsArticle
import com.galarzaa.tibiakt.core.section.news.shared.model.NewsCategory
import kotlinx.datetime.LocalDate

@BuilderDsl
internal inline fun newsBuilder(block: NewsBuilder.() -> Unit): NewsBuilder = NewsBuilder().apply(block)

@BuilderDsl
internal inline fun news(block: NewsBuilder.() -> Unit): NewsArticle = newsBuilder(block).build()

/** Builder for [NewsArticle] instances. */
@BuilderDsl
internal class NewsBuilder : TibiaKtBuilder<NewsArticle> {
    var id: Int? = null
    lateinit var title: String
    lateinit var category: NewsCategory
    lateinit var publishedOn: LocalDate
    lateinit var content: String
    var threadId: Int? = null

    override fun build() = NewsArticle(
        id = requireField(id, "id"),
        title = requireField(::title.isInitialized, "title") { title },
        category = requireField(::category.isInitialized, "category") { category },
        publishedOn = requireField(::publishedOn.isInitialized, "publishedOn") { publishedOn },
        content = requireField(::content.isInitialized, "content") { content },
        threadId = threadId,
    )
}
