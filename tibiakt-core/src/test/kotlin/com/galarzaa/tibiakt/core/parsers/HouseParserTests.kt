package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.StringSpec

class HouseParserTests : StringSpec({
    "Parse a rented house"{
        val house = HouseParser.fromContent(getResource("houses/houseRented.txt"))

    }
})