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

package com.galarzaa.tibiakt.core.section.community.guild.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.domain.character.Vocation
import com.galarzaa.tibiakt.core.section.community.guild.model.Guild
import com.galarzaa.tibiakt.core.section.community.guild.model.GuildHall
import com.galarzaa.tibiakt.core.section.community.guild.model.GuildInvite
import com.galarzaa.tibiakt.core.section.community.guild.model.GuildMember
import kotlinx.datetime.LocalDate

@BuilderDsl
internal inline fun guildBuilder(block: GuildBuilder.() -> Unit): GuildBuilder = GuildBuilder().apply(block)

@BuilderDsl
internal inline fun guild(block: GuildBuilder.() -> Unit): Guild = guildBuilder(block).build()

/** Builder for [Guild] instances. */
@BuilderDsl
internal class GuildBuilder : TibiaKtBuilder<Guild> {
    var world: String? = null
    var name: String? = null
    var logoUrl: String? = null
    var description: String? = null
    var foundedOn: LocalDate? = null
    var isActive: Boolean = false
    var areApplicationsOpen: Boolean = false
    var homepage: String? = null
    var guildHall: GuildHall? = null
    var disbandsOn: LocalDate? = null
    var disbandingReason: String? = null
    val members: MutableList<GuildMember> = mutableListOf()
    val invited: MutableList<GuildInvite> = mutableListOf()

    fun guildHall(name: String, paidUntil: LocalDate): GuildBuilder =
        apply { guildHall = GuildHall(name, paidUntil) }

    fun guildHall(guildHall: GuildHall): GuildBuilder = apply { this.guildHall = guildHall }

    fun addMember(
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
    fun addMember(block: GuildMemberBuilder.() -> Unit): Boolean =
        members.add(GuildMemberBuilder().apply(block).build())

    fun addInvite(name: String, inviteDate: LocalDate): GuildBuilder =
        apply { invited.add(GuildInvite(name, inviteDate)) }

    override fun build(): Guild = Guild(
        world = world ?: error("world is required"),
        name = name ?: error("name is required"),
        logoUrl = logoUrl ?: error("logoUrl is required"),
        description = description,
        foundedOn = foundedOn ?: error("foundedOn is required"),
        isActive = isActive,
        areApplicationsOpen = areApplicationsOpen,
        homepageUrl = homepage,
        guildHall = guildHall,
        members = members,
        invited = invited,
        disbandsOn = disbandsOn,
        disbandingReason = disbandingReason,
    )

    class GuildMemberBuilder : TibiaKtBuilder<GuildMember> {
        lateinit var rank: String
        lateinit var name: String
        var title: String? = null
        lateinit var vocation: Vocation
        var level: Int = 0
        lateinit var joiningDate: LocalDate
        var isOnline: Boolean = false

        override fun build(): GuildMember = GuildMember(
            rank = if (::rank.isInitialized) rank else error("rank is required"),
            name = if (::name.isInitialized) name else error("name is required"),
            title = title,
            vocation = if (::vocation.isInitialized) vocation else error("vocation is required"),
            level = level,
            joinedOn = if (::joiningDate.isInitialized) joiningDate else error("joiningDate is required"),
            isOnline = isOnline,
        )
    }
}
