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
 * Bounty talisman details shown in an auction.
 *
 * @property points The total bounty points available.
 * @property effects The list of bounty talisman effects.
 */
@Serializable
public data class BountyTalisman(
    val points: Int,
    val effects: List<BountyTalismanEffect>,
)

/**
 * A bounty talisman effect.
 *
 * @property effect The effect description.
 * @property level The level of the effect.
 * @property value The effect value as percentage.
 */
@Serializable
public data class BountyTalismanEffect(
    val effect: String,
    val level: Int,
    val value: Double,
)
