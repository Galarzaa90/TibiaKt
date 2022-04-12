package com.galarzaa.tibiakt.client

import java.io.IOException


object TestResources {
    fun getResource(path: String): String {
        return this::class.java.getResource("/$path")?.readText()
            ?: throw IOException("Test resource $path not found")
    }
}
