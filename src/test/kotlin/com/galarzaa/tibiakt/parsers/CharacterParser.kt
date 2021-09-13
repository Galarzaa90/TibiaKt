package com.galarzaa.tibiakt.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CharacterParserTest {

    @Test
    fun fromContent() {
        val result = CharacterParser.fromContent(getResource("character.txt"))

        assertNotNull(result)
        assertEquals("Galarzaa Fidera", result.name)
    }
}
