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
