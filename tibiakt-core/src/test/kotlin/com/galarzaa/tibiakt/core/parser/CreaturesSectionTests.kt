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
import com.galarzaa.tibiakt.core.section.library.creature.parser.CreaturesSectionParser
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.shouldBe

class CreaturesSectionTests : FunSpec({
    test("Creatures section") {
        val creaturesSection = CreaturesSectionParser.fromContent(getResource("creaturesSection/creatureList.txt"))
        with(creaturesSection.boostedCreature) {
            name shouldBe "Mooh'tah Warrior"
            identifier shouldBe "moohtahwarrior"
        }
        creaturesSection.creatures shouldHaveAtLeastSize 600
    }
})
