package com.galarzaa.tibiakt.parsers

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class CharacterParserTest {

    @Test
    fun fromContent() {
        val content = javaClass.classLoader.getResource("character.txt").readText()
        val result = CharacterParser.fromContent(content)

        assertNotNull(result)
        assertEquals("Galarzaa Fidera", result.name)
    }
}