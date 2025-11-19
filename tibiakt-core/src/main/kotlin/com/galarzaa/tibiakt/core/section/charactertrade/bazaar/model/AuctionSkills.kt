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
 * The skill levels of the character in the auction.
 *
 * Decimal values represent the percentage of progress towards the next level.
 *
 * @property axeFighting The current Axe Fighting level.
 * @property clubFighting The current Club Fighting level.
 * @property distanceFighting The current Distance Fighting level.
 * @property fishing The current Fishing level.
 * @property fistFighting The current Fist Fighting level.
 * @property magicLevel The current Magic Level.
 * @property shielding The current Shielding level.
 * @property swordFighting The current Sword Fighting level.
 */
@Serializable
public data class AuctionSkills(
    val axeFighting: Double,
    val clubFighting: Double,
    val distanceFighting: Double,
    val fishing: Double,
    val fistFighting: Double,
    val magicLevel: Double,
    val shielding: Double,
    val swordFighting: Double,
)
