/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.tibiakt.core.section.library.urls

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain

class LibraryUrlsTests : FunSpec({

    test("creaturesUrl") {
        val url = creaturesUrl()

        url shouldContain "/library"
        url shouldContain "subtopic=creatures"
    }

    test("boostableBossesUrl") {
        val url = boostableBossesUrl()

        url shouldContain "/library"
        url shouldContain "subtopic=boostablebosses"
    }

    test("creatureUrl") {
        val url = creatureUrl("dragon")

        url shouldContain "/library"
        url shouldContain "subtopic=creatures"
        url shouldContain "race=dragon"
    }
})
