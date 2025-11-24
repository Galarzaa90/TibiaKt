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

package com.galarzaa.tibiakt.core.section.forum.cmpost.parser

import com.galarzaa.tibiakt.TestUtilities.getResource
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class CMPostArchiveParserTests : FunSpec({
    test("CM Post Archive initial page"){
        val cmPostArchive = CMPostArchiveParser.fromContent(getResource("cmPostArchive/cmPostArchiveInitial.txt"))
        cmPostArchive shouldNotBe null

        cmPostArchive.resultsCount shouldBe 0
        cmPostArchive.currentPage shouldBe 1
        cmPostArchive.totalPages shouldBe 1
        cmPostArchive.entries shouldHaveSize 0
    }

    test("CM Post Archive with results but single page"){
        val cmPostArchive = CMPostArchiveParser.fromContent(getResource("cmPostArchive/cmPostArchiveNoPages.txt"))
        cmPostArchive shouldNotBe null

        cmPostArchive.resultsCount shouldBeGreaterThan 0
        cmPostArchive.currentPage shouldBe 1
        cmPostArchive.totalPages shouldBe 1
        cmPostArchive.entries shouldHaveAtLeastSize 1
    }

    test("CM Post Archive with results in multiple pages"){
        val cmPostArchive = CMPostArchiveParser.fromContent(getResource("cmPostArchive/cmPostArchivePages.txt"))
        cmPostArchive shouldNotBe null

        cmPostArchive.resultsCount shouldBeGreaterThan 0
        cmPostArchive.currentPage shouldBe 1
        cmPostArchive.totalPages shouldBeGreaterThan 1
        cmPostArchive.entries shouldHaveAtLeastSize 1
    }

    test("CM Post Archive without results"){
        val cmPostArchive = CMPostArchiveParser.fromContent(getResource("cmPostArchive/cmPostArchiveNoResults.txt"))
        cmPostArchive shouldNotBe null

        cmPostArchive.resultsCount shouldBe  0
        cmPostArchive.currentPage shouldBe 1
        cmPostArchive.totalPages shouldBe  1
        cmPostArchive.entries shouldHaveSize  0
    }

})
