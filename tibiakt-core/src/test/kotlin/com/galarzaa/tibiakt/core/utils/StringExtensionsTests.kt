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
