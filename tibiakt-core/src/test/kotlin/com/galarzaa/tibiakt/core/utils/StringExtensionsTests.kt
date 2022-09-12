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

package com.galarzaa.tibiakt.core.utils

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class StringExtensionsTests : StringSpec({

    "splitList" {
        "One, Two, Three and Four".splitList() shouldBe listOf("One", "Two", "Three", "Four")
        "A, B, C , D".splitList() shouldBe listOf("A", "B", "C", "D")
        (null as String?).splitList() shouldBe emptyList()
        "".splitList() shouldBe emptyList()
    }

    "remove" {
        "H_e_l_l_o".remove("_") shouldBe "Hello"
        "Hello".remove("L") shouldBe "Hello"
        "Hello".remove("L", true) shouldBe "Heo"
    }

    "clean" {
        " Hello ".clean() shouldBe "Hello"
        "Lorem\u00A0Ipsum".clean() shouldBe "Lorem Ipsum"
        "HTML&#xa0;String".clean() shouldBe "HTML String"
    }
})
