@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.utils.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

@Serializable
data class WorldOverview(
    val overallMaximumCount: Int,
    val overallMaximumCountDateTime: Instant,
    val worlds: List<WorldEntry> = emptyList(),
    val tournamentWorlds: List<WorldEntry> = emptyList(),
)