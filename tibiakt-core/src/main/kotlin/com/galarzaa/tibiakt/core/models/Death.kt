@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

@Serializable
data class Death(val timestamp: Instant, val level: Int, val killers: List<Killer>, val assists: List<Killer>)