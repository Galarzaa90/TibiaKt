package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.guild.Guild
import com.galarzaa.tibiakt.core.models.guild.GuildHall
import com.galarzaa.tibiakt.core.models.guild.GuildInvite
import com.galarzaa.tibiakt.core.models.guild.GuildMember
import java.time.LocalDate

class GuildBuilder {
    private var world: String? = null
    private var name: String? = null
    private var logoUrl: String? = null
    private var description: String? = null
    private var foundingDate: LocalDate? = null
    private var isActive: Boolean = false
    private var applicationsOpen: Boolean = false
    private var homepage: String? = null
    private var guildHall: GuildHall? = null
    private var disbandingDate: LocalDate? = null
    private var disbandingReason: String? = null
    private val members: MutableList<GuildMember> = mutableListOf()
    private val invited: MutableList<GuildInvite> = mutableListOf()

    fun name(name: String) = apply { this.name = name }
    fun description(description: String?) = apply { this.description = description }
    fun logoUrl(logoUrl: String?) = apply { this.logoUrl = logoUrl }
    fun world(world: String?) = apply { this.world = world }
    fun isActive(isActive: Boolean) = apply { this.isActive = isActive }
    fun applicationsOpen(applicationsOpen: Boolean) = apply { this.applicationsOpen = applicationsOpen }
    fun homepage(homepage: String) = apply { this.homepage = homepage }
    fun foundingDate(foundingDate: LocalDate) = apply { this.foundingDate = foundingDate }
    fun disbanding(date: LocalDate, reason: String) = apply { disbandingDate = date; disbandingReason = reason }
    fun guildHall(name: String, paidUntil: LocalDate) = apply { guildHall = GuildHall(name, paidUntil) }

    fun addMember(
        rank: String,
        name: String,
        title: String?,
        vocation: Vocation,
        level: Int,
        joiningDate: LocalDate,
        isOnline: Boolean
    ) = apply {
        members.add(GuildMember(name, rank, title, level, vocation, joiningDate, isOnline))
    }

    fun addInvite(name: String, inviteDate: LocalDate) = apply { invited.add(GuildInvite(name, inviteDate)) }

    fun build(): Guild {
        return Guild(
            world = world ?: throw IllegalStateException("world is required"),
            name = name ?: throw IllegalStateException("name is required"),
            logoUrl = logoUrl ?: throw IllegalStateException("logoUrl is required"),
            description = description,
            foundingDate = foundingDate ?: throw IllegalStateException("name is required"),
            isActive = isActive,
            applicationsOpen = applicationsOpen,
            homepage = homepage,
            guildHall = guildHall,
            members = members,
            invited = invited,
            disbandingDate = disbandingDate,
            disbandingReason = disbandingReason,
        )
    }
}