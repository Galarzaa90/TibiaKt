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

class CreaturesSectionTests : StringSpec({
    "Parsing section" {
        val creaturesSection = CreaturesSectionParser.fromContent(getResource("creatures/creaturesSection.txt"))
        with(creaturesSection.boostedCreature) {
            name shouldBe "Rot Elemental"
            identifier shouldBe "rotelemental"
        }
        creaturesSection.creatures shouldHaveSize 553
    }
})
