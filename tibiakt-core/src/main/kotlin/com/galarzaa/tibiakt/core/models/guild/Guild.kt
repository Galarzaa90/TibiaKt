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

import com.galarzaa.tibiakt.core.collections.offsetStart
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate

/**
 * A Tibia guild.
 *
 * @property world The world where the guild is in.
 * @property logoUrl The URL to the guild's logo, or the default logo.
 * @property description The description of the guild.
 * @property foundedOn The date when the guild was founded.
 * @property isActive Whether the guild is active or still in formation.
 * @property areApplicationsOpen Whether applications are open or not.
 * @property homepageUrl The URL to the guild's homepage, if set.
 * @property guildHall The guildhall rented by the guild.
 * @property disbandsOn The date when the guild is set to be disbanded.
 * @property disbandingReason The reason why it will be disbanded.
 * @property members The list of members in the guild.
 * @property invited The list of characters currently invited to the guild.
 */
@Serializable
public data class Guild(
    override val name: String,
    val world: String,
    val logoUrl: String,
    val description: String?,
    val foundedOn: LocalDate,
    val isActive: Boolean,
    val areApplicationsOpen: Boolean,
    val homepageUrl: String?,
    val guildHall: GuildHall?,
    val disbandsOn: LocalDate?,
    val disbandingReason: String?,
    val members: List<GuildMember>,
    val invited: List<GuildInvite>,
) : BaseGuild {
    /**
     * A list of the guild ranks.
     */
    val ranks: List<String>
        get() = members.map { it.rank }.distinct()

    /** The leader of the guild. */
    val leader: GuildMember
        get() = members.first()

    /** The vice leaders of the guild. */
    val viceLeaders: List<GuildMember>
        get() = members.offsetStart(1).takeWhile { it.rank == members[1].rank }

    /** A mapping of the members by their rank. */
    val membersByRank: Map<String, List<GuildMember>>
        get() = members.groupBy { it.rank }

    /** The number of members in the guild. */
    val memberCount: Int
        get() = members.size

    /**
     * The list of members currently online.
     */
    val onlineMembers: List<GuildMember>
        get() = members.filter { it.isOnline }

    /**
     * The number of online members in the guild.
     */
    val onlineCount: Int
        get() = onlineMembers.count()
}
