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

package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.shouldBe

class BoostableBossesTests : FunSpec({
    test("Boostable Bosses List") {
        val boostableBosses = BoostableBossesParser.fromContent(getResource("boostableBosses/bossList.txt"))
        with(boostableBosses.boostedBoss) {
            name shouldBe "Abyssador"
            identifier shouldBe "abyssador"
        }
        boostableBosses.bosses shouldHaveAtLeastSize 1
    }
})
