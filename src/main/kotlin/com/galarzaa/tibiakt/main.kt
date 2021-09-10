package com.galarzaa.tibiakt

import com.galarzaa.tibiakt.core.Client

suspend fun main() {
    val client = Client()
    val char = client.fetchCharacter("Nezune")
    client.client.close()
}