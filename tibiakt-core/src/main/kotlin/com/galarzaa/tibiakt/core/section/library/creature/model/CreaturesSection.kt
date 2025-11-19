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

package com.galarzaa.tibiakt.core.section.library.creature.model

import com.galarzaa.tibiakt.core.section.library.urls.creaturesUrl
import kotlinx.serialization.Serializable

/**
 * The Creature's section in Tibia.com.
 *
 * @property boostedCreature The currently boosted creature, also known as creature of the day.
 * @property creatures The list of creatures in the library. Not all creatures in-game are visible.
 */
@Serializable
public data class CreaturesSection(
    val boostedCreature: CreatureEntry,
    val creatures: List<CreatureEntry>,
) {
    /**
     * The URL to the creatures section.
     */
    val url: String get() = creaturesUrl()
}
