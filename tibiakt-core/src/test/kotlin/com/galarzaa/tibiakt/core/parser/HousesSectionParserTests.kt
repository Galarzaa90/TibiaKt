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

package com.galarzaa.tibiakt.core.parser

import com.galarzaa.tibiakt.TestUtilities.getResource
import com.galarzaa.tibiakt.core.section.community.house.model.HouseStatus
import com.galarzaa.tibiakt.core.section.community.house.model.HousesSection
import com.galarzaa.tibiakt.core.section.community.house.parser.HousesSectionParser
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAtLeastOne
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

class HousesSectionParserTests : FunSpec({
    test("Houses section"){
        val housesSection = HousesSectionParser.fromContent(getResource("housesSection/housesSection.txt"))

        housesSection.shouldBeInstanceOf<HousesSection>()

        with(housesSection){
            world shouldBe "Alumbra"
            town shouldBe "Carlin"
            entries shouldHaveAtLeastSize 1
        }
    }
    test("Houses section empty"){
        val housesSection = HousesSectionParser.fromContent(getResource("housesSection/housesSectionEmpty.txt"))

        housesSection.shouldBeInstanceOf<HousesSection>()

        with(housesSection){
            entries shouldHaveSize 0
        }
    }
    test("Houses section with auctioned houses") {
        val housesSection =
            HousesSectionParser.fromContent(getResource("housesSection/housesSectionWithAuctionedHouses.txt"))

        housesSection.shouldBeInstanceOf<HousesSection>()

        with(housesSection) {
            entries shouldHaveAtLeastSize 1
            entries.forAtLeastOne {
                it.status shouldBe HouseStatus.AUCTIONED
                it.highestBid shouldNotBe null
                it.timeLeft shouldNotBe null
            }
            entries.forAtLeastOne {
                it.status shouldBe HouseStatus.AUCTIONED
                it.highestBid shouldBe null
                it.timeLeft shouldBe null
            }
        }
    }
    test("Houses section not found"){
        val housesSection = HousesSectionParser.fromContent(getResource("housesSection/housesSectionNotFound.txt"))

        housesSection.shouldBeNull()
    }
})
