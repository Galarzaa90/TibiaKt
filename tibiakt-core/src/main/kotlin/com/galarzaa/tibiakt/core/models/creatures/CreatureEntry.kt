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

package com.galarzaa.tibiakt.core.models.creatures

import com.galarzaa.tibiakt.core.utils.getCreatureUrl
import kotlinx.serialization.Serializable

/**
 * A creature in the [CreaturesSection].
 *
 * @property name The name of the creature. Usually in plural, except for [CreaturesSection.boostedCreature].
 * @property identifier Internal name for the creature's race. Used for links and images.
 */
@Serializable
public data class CreatureEntry(
    override val name: String,
    override val identifier: String,
) : BaseCreatureEntry {
    /**
     * The URL to the creature's page.
     */
    val url: String get() = getCreatureUrl(identifier)
}
