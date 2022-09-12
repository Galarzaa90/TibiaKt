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
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDate

class CMPostArchiveParserTests : StringSpec({
    "Parsing with results"{
        val cmPostArchive = CMPostArchiveParser.fromContent(getResource("forums/cmPostArchiveWithItems.txt"))
        cmPostArchive shouldNotBe null
        cmPostArchive.startDate shouldBe LocalDate.of(2022, 1, 13)
        cmPostArchive.endDate shouldBe LocalDate.of(2022, 1, 20)
        cmPostArchive.currentPage shouldBe 1
        cmPostArchive.totalPages shouldBe 1
        cmPostArchive.resultsCount shouldBe 9
        cmPostArchive.entries shouldHaveSize 9
    }
    "Parsing with no results"{
        val cmPostArchive = CMPostArchiveParser.fromContent(getResource("forums/cmPostArchiveEmpty.txt"))
        cmPostArchive shouldNotBe null
        cmPostArchive.startDate shouldBe LocalDate.of(2000, 1, 13)
        cmPostArchive.endDate shouldBe LocalDate.of(2000, 1, 20)
        cmPostArchive.currentPage shouldBe 1
        cmPostArchive.totalPages shouldBe 1
        cmPostArchive.resultsCount shouldBe 0
        cmPostArchive.entries shouldHaveSize 0
    }
})
