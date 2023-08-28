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
import com.galarzaa.tibiakt.core.models.house.HousesSection
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAll
import io.kotest.inspectors.forAtLeastOne
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

class HousesSectionParserTests : FunSpec({
    test("Houses section"){
        val housesSection = HousesSectionParser.fromContent(getResource("house/housesSection.txt"))

        housesSection.shouldBeInstanceOf<HousesSection>()

        with(housesSection){
            world shouldBe "Alumbra"
            town shouldBe "Carlin"
            entries shouldHaveAtLeastSize 1
        }
    }
    test("Houses section empty"){
        val housesSection = HousesSectionParser.fromContent(getResource("house/housesSectionEmpty.txt"))

        housesSection.shouldBeInstanceOf<HousesSection>()

        with(housesSection){
            entries shouldHaveSize 0
        }
    }
    test("Houses section not found"){
        val housesSection = HousesSectionParser.fromContent(getResource("house/housesSectionNotFound.txt"))

        housesSection.shouldBeNull()
    }
})
