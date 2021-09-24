package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import com.galarzaa.tibiakt.models.NewsCategory
import com.galarzaa.tibiakt.models.NewsType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.date.shouldBeBefore

class NewsArchiveParserTests : StringSpec({
    "Parsing the news archive"{
        val newsArchive = NewsArchiveParser.fromContent(getResource("news/newsArchive.txt"))
        newsArchive.entries shouldHaveSize 11
        newsArchive.types shouldHaveSize 2
        newsArchive.types shouldNotContain NewsType.FEATURED_ARTICLE
        newsArchive.categories shouldHaveSize 3
        newsArchive.categories shouldNotContain NewsCategory.SUPPORT
        newsArchive.startDate shouldBeBefore newsArchive.endDate
    }
})