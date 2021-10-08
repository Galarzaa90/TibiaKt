package com.galarzaa.tibiakt.core.models

import kotlinx.serialization.Serializable

@Serializable
data class KillStatistics(
    val world: String,
    val entries: Map<String, KillsStatisticEntry> = mapOf(),
    val total: KillsStatisticEntry
)

