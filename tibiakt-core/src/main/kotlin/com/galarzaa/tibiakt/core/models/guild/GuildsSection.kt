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

package com.galarzaa.tibiakt.core.models.guild

import com.galarzaa.tibiakt.core.net.worldGuildsUrl
import kotlinx.serialization.Serializable

/**
 * The list of [Guild]s in a world.
 *
 * @property world The name of the world the guilds belong to.
 * @property guilds A list of all the guilds in the world.
 */
@Serializable
public data class GuildsSection(
    val world: String,
    val guilds: List<GuildEntry>,
) {
    /**
     * The URL to this guilds section.
     */
    val url: String
        get() = worldGuildsUrl(world)

    /**
     * A filtered version of [guilds] containing only active guilds.
     */
    val activeGuilds: List<GuildEntry> get() = guilds.filter { it.isActive }

    /**
     * A filtered version of [guilds] containing only guilds in formation.
     */
    val guildsInFormation: List<GuildEntry> get() = guilds.filter { !it.isActive }
}
