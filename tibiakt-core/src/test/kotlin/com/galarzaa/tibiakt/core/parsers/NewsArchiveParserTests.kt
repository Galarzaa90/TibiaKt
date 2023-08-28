/*
 * Copyright © 2022 Allan Galarza
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

package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.enums.NewsType
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.date.shouldBeBefore
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class NewsArchiveParserTests : FunSpec({
    test("Initial news archive") {
        val newsArchive = NewsArchiveParser.fromContent(getResource("news/newsArchiveInitial.txt"))

        newsArchive.categories shouldHaveSize 5
        newsArchive.types shouldHaveSize 3
        newsArchive.entries.shouldBeEmpty()
    }

    test("News archive with filters used") {
        val newsArchive = NewsArchiveParser.fromContent(getResource("news/newsArchiveWithFilters.txt"))

        with(newsArchive) {
            startDate shouldBe LocalDate.of(2019, 3, 25)
            endDate shouldBe LocalDate.of(2019, 5, 25)
            newsArchive.categories shouldHaveSize 5
            newsArchive.types shouldHaveSize 3
            entries.shouldNotBeEmpty()
        }
    }

    test("News archive with no results"){
        val newsArchive = NewsArchiveParser.fromContent(getResource("news/newsArchiveEmpty.txt"))

        with(newsArchive) {
            startDate shouldBe LocalDate.of(2023, 4, 13)
            endDate shouldBe LocalDate.of(2023, 4, 15)
            newsArchive.categories shouldHaveSize 5
            newsArchive.types shouldHaveSize 1
            entries.shouldBeEmpty()
        }
    }

    test("News archive with an error message"){
        val newsArchive = NewsArchiveParser.fromContent(getResource("news/newsArchiveError.txt"))

        with(newsArchive) {
            entries.shouldBeEmpty()
        }
    }
})
