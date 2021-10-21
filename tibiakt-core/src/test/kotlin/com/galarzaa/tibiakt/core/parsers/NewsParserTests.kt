package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import com.galarzaa.tibiakt.core.enums.NewsCategory
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDate

class NewsParserTests : StringSpec({
    "News ticker" {
        val news = NewsParser.fromContent(getResource("news/newsTicker.txt"))
        news shouldNotBe null
        news!!.title shouldBe "News Ticker"
        news.category shouldBe NewsCategory.COMMUNITY
        news.date shouldBe LocalDate.of(2021, 9, 8)
        news.threadId shouldBe null
        news.content shouldNotBe null
    }

    "Featured article" {
        val news = NewsParser.fromContent(getResource("news/newsFeaturedArticle.txt"))
        news shouldNotBe null
        news!!.title shouldBe "Memories from 2020"
        news.category shouldBe NewsCategory.COMMUNITY
        news.date shouldBe LocalDate.of(2021, 2, 4)
        news.threadId shouldBe 4846119
        news.content shouldNotBe null
    }

    "News article with a discussion thread" {
        val news = NewsParser.fromContent(getResource("news/newsWithDiscussionThread.txt"))
        news shouldNotBe null
        news!!.title shouldBe "Sneak Peek: Tibia Observer"
        news.category shouldBe NewsCategory.DEVELOPMENT
        news.date shouldBe LocalDate.of(2021, 9, 22)
        news.threadId shouldBe 4889730
        news.content shouldNotBe null
    }

    "News not found" {
        val news = NewsParser.fromContent(getResource("news/newsNotFound.txt"))
        news shouldBe null
    }
})