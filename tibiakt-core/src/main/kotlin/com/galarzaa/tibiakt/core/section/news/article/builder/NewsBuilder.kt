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
import com.galarzaa.tibiakt.core.section.news.article.model.News
import com.galarzaa.tibiakt.core.section.news.shared.model.NewsCategory
import kotlinx.datetime.LocalDate

@BuilderDsl
public inline fun newsBuilder(block: NewsBuilder.() -> Unit): NewsBuilder = NewsBuilder().apply(block)

@BuilderDsl
public inline fun news(block: NewsBuilder.() -> Unit): News = newsBuilder(block).build()

/** Builder for [News] instances. */
@BuilderDsl
public class NewsBuilder : TibiaKtBuilder<News> {
    public var id: Int? = null
    public lateinit var title: String
    public lateinit var category: NewsCategory
    public lateinit var publishedOn: LocalDate
    public lateinit var content: String
    public var threadId: Int? = null
    override fun build(): News {
        return News(
            id = id ?: error("id is required"),
            title = if (::title.isInitialized) title else error("title is required"),
            category = if (::category.isInitialized) category else error("category is required"),
            publishedOn = if (::publishedOn.isInitialized) publishedOn else error("publishedOn is required"),
            content = if (::content.isInitialized) content else error("content is required"),
            threadId = threadId,
        )
    }
}
