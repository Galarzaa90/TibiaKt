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
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.BazaarType
import com.galarzaa.tibiakt.core.section.charactertrade.parser.CharacterBazaarParser
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class CharacterBazaarParserTests : FunSpec({
    test("Current auctions") {
        val bazaar = CharacterBazaarParser.fromContent(getResource("characterBazaar/bazaarCurrentAuctions.txt"))

        bazaar.type shouldBe BazaarType.CURRENT
        bazaar.entries shouldHaveAtLeastSize 0
    }
    test("Current auctions with filters") {
        val bazaar =
            CharacterBazaarParser.fromContent(getResource("characterBazaar/bazaarCurrentAuctionsWithFilters.txt"))

        bazaar.type shouldBe BazaarType.CURRENT
        with(bazaar.filters) {
            world shouldNotBe null
            pvpType shouldNotBe null
            battlEyeType shouldNotBe null
            vocation shouldNotBe null
            minimumLevel shouldNotBe null
            maximumLevel shouldNotBe null
            minimumSkillLevel shouldNotBe null
            maximumSkillLevel shouldNotBe null
            skill shouldNotBe null
            orderBy shouldNotBe null
            orderDirection shouldNotBe null
            searchType shouldNotBe null
            searchString shouldNotBe null
        }
    }

    test("Auction History") {
        val bazaar = CharacterBazaarParser.fromContent(getResource("characterBazaar/bazaarHistory.txt"))

        bazaar.type shouldBe BazaarType.HISTORY
        bazaar.entries shouldHaveAtLeastSize 0
    }

    test("Auction History empty") {
        val bazaar = CharacterBazaarParser.fromContent(getResource("characterBazaar/bazaarHistoryEmpty.txt"))

        bazaar.type shouldBe BazaarType.HISTORY
        bazaar.entries shouldHaveSize 0
    }
})
