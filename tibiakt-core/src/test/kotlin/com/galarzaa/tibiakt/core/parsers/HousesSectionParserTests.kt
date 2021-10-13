package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class HousesSectionParserTests : StringSpec({
    "Parse house list"{
        val housesSection = HousesSectionParser.fromContent(getResource("houses/houseList.txt"))
        housesSection.world shouldBe "Ardera"
    }
})