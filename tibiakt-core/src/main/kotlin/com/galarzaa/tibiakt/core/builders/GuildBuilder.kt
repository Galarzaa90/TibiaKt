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

import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.guild.Guild
import com.galarzaa.tibiakt.core.models.guild.GuildHall
import com.galarzaa.tibiakt.core.models.guild.GuildInvite
import com.galarzaa.tibiakt.core.models.guild.GuildMember
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.LocalDate

@BuilderDsl
public inline fun guildBuilder(block: GuildBuilder.() -> Unit): GuildBuilder = GuildBuilder().apply(block)

@BuilderDsl
public inline fun guild(block: GuildBuilder.() -> Unit): Guild = guildBuilder(block).build()

/** Builder for [Guild] instances. */
@BuilderDsl
public class GuildBuilder : TibiaKtBuilder<Guild> {
    public var world: String? = null
    public var name: String? = null
    public var logoUrl: String? = null
    public var description: String? = null
    public var foundingDate: LocalDate? = null
    public var isActive: Boolean = false
    public var applicationsOpen: Boolean = false
    public var homepage: String? = null
    public var guildHall: GuildHall? = null
    public var disbandingDate: LocalDate? = null
    public var disbandingReason: String? = null
    public val members: MutableList<GuildMember> = mutableListOf()
    public val invited: MutableList<GuildInvite> = mutableListOf()

    public fun guildHall(name: String, paidUntil: LocalDate): GuildBuilder =
        apply { guildHall = GuildHall(name, paidUntil) }

    public fun guildHall(guildHall: GuildHall): GuildBuilder = apply { this.guildHall = guildHall }

    public fun addMember(
        rank: String,
        name: String,
        title: String?,
        vocation: Vocation,
        level: Int,
        joiningDate: LocalDate,
        isOnline: Boolean,
    ): GuildBuilder = apply {
        members.add(GuildMember(name, rank, title, level, vocation, joiningDate, isOnline))
    }

    @BuilderDsl
    public fun addMember(block: GuildMemberBuilder.() -> Unit): Boolean =
        members.add(GuildMemberBuilder().apply(block).build())

    public fun addInvite(name: String, inviteDate: LocalDate): GuildBuilder =
        apply { invited.add(GuildInvite(name, inviteDate)) }

    override fun build(): Guild = Guild(
        world = world ?: error("world is required"),
        name = name ?: error("name is required"),
        logoUrl = logoUrl ?: error("logoUrl is required"),
        description = description,
        foundingDate = foundingDate ?: error("name is required"),
        isActive = isActive,
        applicationsOpen = applicationsOpen,
        homepage = homepage,
        guildHall = guildHall,
        members = members,
        invited = invited,
        disbandingDate = disbandingDate,
        disbandingReason = disbandingReason,
    )

    public class GuildMemberBuilder : TibiaKtBuilder<GuildMember> {
        public lateinit var rank: String
        public lateinit var name: String
        public var title: String? = null
        public lateinit var vocation: Vocation
        public var level: Int = 0
        public lateinit var joiningDate: LocalDate
        public var isOnline: Boolean = false

        override fun build(): GuildMember = GuildMember(
            rank = if (::rank.isInitialized) rank else error("rank is required"),
            name = if (::name.isInitialized) name else error("name is required"),
            title = title,
            vocation = if (::vocation.isInitialized) vocation else error("vocation is required"),
            level = level,
            joiningDate = if (::joiningDate.isInitialized) joiningDate else error("joiningDate is required"),
            isOnline = isOnline,
        )
    }
}
