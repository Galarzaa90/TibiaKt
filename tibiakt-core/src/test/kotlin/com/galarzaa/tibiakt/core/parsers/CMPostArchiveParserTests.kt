package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.StringSpec

class CMPostArchiveParserTests : StringSpec({
    "Parsing with results"{
        val cmPostArchve = CMPostArchiveParser.fromContent(getResource("forums/cmPostArchiveWithItems.txt"))
    }
})
