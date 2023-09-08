/*
 * Copyright © 2023 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.galarzaa.tibiakt.core.models.character

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * A death by a [Character].
 *
 * @property timestamp The date and time when the death happened.
 * @property level The level of the character when they died.
 * @property killers The list of killers.
 * @property assists The list of characters that assisted in the death without dealing damage.
 */
@Serializable
public data class Death(
    val timestamp: Instant,
    val level: Int,
    val killers: List<DeathParticipant>,
    val assists: List<DeathParticipant>,
) {
    /**
     * Whether this death was caused by players.
     */
    val isByPlayers: Boolean get() = killers.any { it.isPlayer } || assists.any()
}
