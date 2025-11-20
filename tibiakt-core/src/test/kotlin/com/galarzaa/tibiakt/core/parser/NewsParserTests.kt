/*
 * Copyright © 2023 Allan Galarza
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

package com.galarzaa.tibiakt.core.parser

import com.galarzaa.tibiakt.TestUtilities.getResource
import com.galarzaa.tibiakt.core.section.news.article.model.NewsArticle
import com.galarzaa.tibiakt.core.section.news.article.parser.NewsParser
import com.galarzaa.tibiakt.core.section.news.shared.model.NewsCategory
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

class NewsParserTests : FunSpec({
    isolationMode = IsolationMode.SingleInstance

    test("News post with discussion thread") {
        val news = NewsParser.fromContent(getResource("news/newsPostWithDiscussionThread.txt"))

        news.shouldBeInstanceOf<NewsArticle>()
        news.threadId shouldNotBe null
        news.threadUrl shouldNotBe null
    }
    test("News ticker") {
        val news = NewsParser.fromContent(getResource("news/newsTicker.txt"))

        news.shouldBeInstanceOf<NewsArticle>()
        news.title shouldBe "News Ticker"
    }
    test("Featured article") {
        val news = NewsParser.fromContent(getResource("news/newsFeaturedArticle.txt"))

        news.shouldBeInstanceOf<NewsArticle>()
        news.category shouldBe NewsCategory.COMMUNITY
    }
    test("News not found") {
        val news = NewsParser.fromContent(getResource("news/newsNotFound.txt"))

        news shouldBe null
    }
})
