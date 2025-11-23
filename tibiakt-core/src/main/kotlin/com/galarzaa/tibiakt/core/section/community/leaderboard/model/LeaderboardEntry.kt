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

package com.galarzaa.tibiakt.core.section.community.leaderboard.model

import com.galarzaa.tibiakt.core.domain.character.BaseCharacter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Entry type for Leaderboards.
 *
 * @property rank The rank of the character.
 * @property dromeLevel The drome level of the character.
 */
@Serializable
public sealed interface LeaderboardEntry {
    public val rank: Int
    public val dromeLevel: Int

    /**
     * A leaderboard entry belonging to a normal (non-deleted) character.
     */
    @Serializable
    @SerialName("character")
    public data class Character(
        override val rank: Int,
        override val dromeLevel: Int,
        override val name: String,
    ) : LeaderboardEntry, BaseCharacter

    /**
     * A leaderboard entry belonging to a deleted character.
     */
    @Serializable
    @SerialName("deleted")
    public data class Deleted(
        override val rank: Int,
        override val dromeLevel: Int,
    ) : LeaderboardEntry
}
