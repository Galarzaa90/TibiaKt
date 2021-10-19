package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

@Serializable
data class BestiaryEntry(
    val name: String,
    val kills: Long,
    val step: Int,
) {
    val isComplete get() = step == 4
}