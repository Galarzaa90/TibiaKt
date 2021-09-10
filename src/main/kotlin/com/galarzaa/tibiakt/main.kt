package com.galarzaa.tibiakt

import com.galarzaa.tibiakt.core.Client

suspend fun main() {
    val client = Client()
    client.fetchCharacter("Nezune")
    client.client.close()
}