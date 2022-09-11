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
import com.galarzaa.tibiakt.core.enums.HouseOrder
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAll
import io.kotest.inspectors.forAtLeastOne
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class HousesSectionParserTests : StringSpec({
    "Parse house list"{
        val housesSection = HousesSectionParser.fromContent(getResource("houses/houseList.txt"))
        housesSection shouldNotBe null
        housesSection!!.world shouldBe "Ardera"
        housesSection.town shouldBe "Carlin"
        housesSection.status shouldBe null
        housesSection.type shouldBe HouseType.HOUSE
        housesSection.order shouldBe HouseOrder.NAME
        housesSection.entries shouldHaveSize 97
        housesSection.entries.also {
            it.forAll { house ->
                house.type shouldBe housesSection.type
                house.town shouldBe housesSection.town
                house.world shouldBe housesSection.world
            }
            it.forAtLeastOne { house ->
                house.status shouldBe HouseStatus.AUCTIONED
                house.highestBid shouldBe 0
                house.timeLeft shouldNotBe null
            }
        }
    }

    "Parse an empty house list"{
        val housesSection = HousesSectionParser.fromContent(getResource("houses/houseListEmpty.txt"))
        housesSection shouldNotBe null
        housesSection!!.world shouldBe "Antica"
        housesSection.town shouldBe "Carlin"
        housesSection.status shouldBe HouseStatus.AUCTIONED
        housesSection.type shouldBe HouseType.HOUSE
        housesSection.order shouldBe HouseOrder.NAME
        housesSection.entries shouldHaveSize 0
    }

    "Parse initial house list (no parameters)"{
        val housesSection = HousesSectionParser.fromContent(getResource("houses/houseListInitial.txt"))
        housesSection shouldBe null
    }
})
