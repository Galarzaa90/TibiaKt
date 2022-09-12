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

import kotlinx.serialization.Serializable

/**
 * A guild listed in the [GuildsSection] of a world.
 *
 * @property description The description of the guild.
 * @property logoUrl The URL to the guild's logo.
 * @property isActive Whether the guild is active or still in formation.
 */
@Serializable
public data class GuildEntry(
    override val name: String,
    val description: String?,
    val logoUrl: String,
    val isActive: Boolean,
) : BaseGuild
