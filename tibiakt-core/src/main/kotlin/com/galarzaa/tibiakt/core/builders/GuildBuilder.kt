package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.guild.Guild
import com.galarzaa.tibiakt.core.models.guild.GuildHall
import com.galarzaa.tibiakt.core.models.guild.GuildInvite
import com.galarzaa.tibiakt.core.models.guild.GuildMember
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.LocalDate

@BuilderDsl
inline fun guildBuilder(block: GuildBuilder.() -> Unit) = GuildBuilder().apply(block)

@BuilderDsl
inline fun guild(block: GuildBuilder.() -> Unit) = guildBuilder(block).build()

@BuilderDsl
class GuildBuilder : TibiaKtBuilder<Guild>() {
    var world: String? = null
    var name: String? = null
    var logoUrl: String? = null
    var description: String? = null
    var foundingDate: LocalDate? = null
    var isActive: Boolean = false
    var applicationsOpen: Boolean = false
    var homepage: String? = null
    var guildHall: GuildHall? = null
    var disbandingDate: LocalDate? = null
    var disbandingReason: String? = null
    val members: MutableList<GuildMember> = mutableListOf()
    val invited: MutableList<GuildInvite> = mutableListOf()

    fun guildHall(name: String, paidUntil: LocalDate) = apply { guildHall = GuildHall(name, paidUntil) }
    fun guildHall(guildHall: GuildHall) = apply { this.guildHall = guildHall }

    fun addMember(
        rank: String,
        name: String,
        title: String?,
        vocation: Vocation,
        level: Int,
        joiningDate: LocalDate,
        isOnline: Boolean,
    ) = apply {
        members.add(GuildMember(name, rank, title, level, vocation, joiningDate, isOnline))
    }

    @BuilderDsl
    fun addMember(block: GuildMemberBuilder.() -> Unit) = members.add(GuildMemberBuilder().apply(block).build())

    fun addInvite(name: String, inviteDate: LocalDate) = apply { invited.add(GuildInvite(name, inviteDate)) }

    override fun build() = Guild(
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

    class GuildMemberBuilder : TibiaKtBuilder<GuildMember>() {
        lateinit var rank: String
        lateinit var name: String
        var title: String? = null
        lateinit var vocation: Vocation
        var level: Int = 0
        lateinit var joiningDate: LocalDate
        var isOnline: Boolean = false

        override fun build() = GuildMember(
            rank = if (::rank.isInitialized) rank else throw IllegalStateException("rank is required"),
            name = if (::name.isInitialized) name else throw IllegalStateException("name is required"),
            title = title,
            vocation = if (::vocation.isInitialized) vocation else throw IllegalStateException("vocation is required"),
            level = level,
            joiningDate = if (::joiningDate.isInitialized) joiningDate else throw IllegalStateException("joiningDate is required"),
            isOnline = isOnline,
        )
    }
}