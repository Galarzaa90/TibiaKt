/*
 * Copyright © 2022 Allan Galarza
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

package com.galarzaa.tibiakt.core.models.leaderboards

import com.galarzaa.tibiakt.core.models.character.BaseCharacter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Base class for Leaderboards entries.
 *
 * @property rank The rank of the character.
 * @property dromeLevel The drome level of the character.
 */
@Serializable
public sealed class BaseLeaderboardsEntry {
    public abstract val rank: Int
    public abstract val dromeLevel: Int
}

/**
 * An entry in the [LeaderboardsEntry].
 */
@Serializable
@SerialName("leaderboardsEntry")
public data class LeaderboardsEntry(
    override val rank: Int,
    override val name: String,
    override val dromeLevel: Int,
) : BaseLeaderboardsEntry(), BaseCharacter

/** A leaderboard entry belonging to a deleted character */
@Serializable
@SerialName("deletedLeaderboardsEntry")
public data class DeletedLeaderboardsEntry(
    override val rank: Int,
    override val dromeLevel: Int,
) : BaseLeaderboardsEntry()
