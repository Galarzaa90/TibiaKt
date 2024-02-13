/*
 * Copyright © 2024 Allan Galarza
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

import kotlinx.serialization.Serializable

/**
 * Represents a participant listed in a death.
 *
 * @property name The name of the participant. If the participant is a summoned creature, this is the summoner's name.
 * @property isPlayer Whether the participant is a player.
 * @property isTraded Whether the character was traded after this death occurred.
 * @property summon The summoned creature that caused this death, if applicable.
 */
@Serializable
public data class DeathParticipant(
    val name: String,
    val isPlayer: Boolean,
    val summon: String?,
    val isTraded: Boolean,
)
