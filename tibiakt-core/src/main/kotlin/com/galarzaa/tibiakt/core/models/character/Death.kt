@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.character

import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

/**
 * A death by a [Character]
 *
 * @property timestamp The date and time when the death happened.
 * @property level The level of the character when they died.
 * @property killers The list of killers.
 * @property assists The list of characters that assisted in the death without dealing damage.
 */
@Serializable
data class Death(val timestamp: Instant, val level: Int, val killers: List<Killer>, val assists: List<Killer>) {
    /**
     * Whether this death was caused by players.
     */
    val isByPlayers: Boolean get() = killers.any { it.isPlayer } || assists.any()
}