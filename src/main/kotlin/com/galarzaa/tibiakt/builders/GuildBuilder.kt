package com.galarzaa.tibiakt.builders

import com.galarzaa.tibiakt.models.Guild
import com.galarzaa.tibiakt.models.GuildHall
import com.galarzaa.tibiakt.models.GuildInvite
import com.galarzaa.tibiakt.models.GuildMember
import java.time.LocalDate

class GuildBuilder {
    var world: String? = null
    var name: String? = null
    var logoUrl: String? = null
    var description: String? = null
    var foundingDate: LocalDate? = null
    var isActive: Boolean = false
    var applicationsOpen: Boolean = false
    val homepage: String? = null
    val guildHall: GuildHall? = null
    val members: MutableList<GuildMember> = mutableListOf()
    val invited: MutableList<GuildInvite> = mutableListOf()

    fun name(name: String) = apply { this.name = name }
    fun description(description: String?) = apply { this.description = description }
    fun logoUrl(logoUrl: String?) = apply { this.logoUrl = logoUrl }
    fun world(world: String?) = apply { this.world = world }
    fun isActive(isActive: Boolean) = apply { this.isActive = isActive }
    fun foundingDate(foundingDate: LocalDate) = apply { this.foundingDate = foundingDate }

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
            invited = invited

        )
    }
}