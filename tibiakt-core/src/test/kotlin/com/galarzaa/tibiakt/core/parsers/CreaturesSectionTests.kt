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