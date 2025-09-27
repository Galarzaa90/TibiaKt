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

package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.guild.GuildEntry
import com.galarzaa.tibiakt.core.models.guild.GuildsSection
import com.galarzaa.tibiakt.core.builders.BuilderDsl

@BuilderDsl
public inline fun guildsSectionBuilder(block: GuildsSectionBuilder.() -> Unit): GuildsSectionBuilder =
    GuildsSectionBuilder().apply(block)

@BuilderDsl
public inline fun guildsSection(block: GuildsSectionBuilder.() -> Unit): GuildsSection =
    guildsSectionBuilder(block).build()

/** Builder for [GuildsSection] instances. */
@BuilderDsl
public class GuildsSectionBuilder : TibiaKtBuilder<GuildsSection> {
    public var world: String? = null
    public val guilds: MutableList<GuildEntry> = mutableListOf()

    public fun addGuild(
        name: String,
        logoUrl: String,
        description: String? = null,
        isActive: Boolean,
    ): GuildsSectionBuilder = apply {
        guilds.add(GuildEntry(name, description, logoUrl, isActive))
    }

    override fun build(): GuildsSection {
        return GuildsSection(
            world = world ?: error("name is required"),
            guilds = guilds
        )
    }
}
