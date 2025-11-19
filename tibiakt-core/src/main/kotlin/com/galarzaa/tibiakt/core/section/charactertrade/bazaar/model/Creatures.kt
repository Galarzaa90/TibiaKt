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

package com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model

import kotlinx.serialization.Serializable

/**
 * A creature entry of an [Auction] character, could be a bestiary or bosstiary entry.
 *
 * @property name The name of the creature.
 * @property kills The number of kills done by the character.
 * @property step The current unlock step of the character.
 */
public sealed interface AuctionCreatureEntry {
    public val name: String
    public val kills: Long
    public val step: Int

    /**
     * Whether the entry is complete or not.
     */
    public val isComplete: Boolean get() = step == 4
}

/**
 * A bosstiary entry of an [Auction] character.
 */
@Serializable
public data class BosstiaryEntry(
    override val name: String,
    override val kills: Long,
    override val step: Int,
) : AuctionCreatureEntry

/**
 * A bestiary entry of an [Auction] character.
 *
 * @property isMasteryUnlocked Whether the character has unlocked the mastery of this creature or not.
 */
@Serializable
public data class BestiaryEntry(
    override val name: String,
    override val kills: Long,
    override val step: Int,
    val isMasteryUnlocked: Boolean,
) : AuctionCreatureEntry
