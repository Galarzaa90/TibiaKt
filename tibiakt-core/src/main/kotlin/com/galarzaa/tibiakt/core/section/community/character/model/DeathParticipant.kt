/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.tibiakt.core.section.community.character.model

import com.galarzaa.tibiakt.core.domain.character.BaseCharacter
import com.galarzaa.tibiakt.core.section.community.urls.characterUrl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** An entity that participated in a [character][CharacterInfo]'s death. */
@Serializable
public sealed interface DeathParticipant {
    /** The name of the participant. */
    public val name: String

    /**
     * A creature that participated in a death.
     *
     * @property name The name of the creature.
     */
    @Serializable
    @SerialName("creature")
    public data class Creature(override val name: String) : DeathParticipant

    /**
     * A player who participated in a death.
     *
     * @property name The name of the player.
     */
    @Serializable
    @SerialName("player")
    public data class Player(
        override val name: String,
        val isTraded: Boolean,
    ) : DeathParticipant, BaseCharacter

    /**
     * A creature that was summoned by a player and participated in a death.
     *
     * @property name The creature that dealt the killing blow.
     * @property summonerName The name of the player that summoned the creature.
     * @property summonerIsTraded Whether the summoner was traded after the death occurred.
     */
    @Serializable
    @SerialName("summon")
    public data class Summon(
        override val name: String,
        val summonerName: String,
        val summonerIsTraded: Boolean,
    ) : DeathParticipant {
        /** URL to the summoner's information page. */
        public val summonerUrl: String get() = characterUrl(summonerName)
    }
}
