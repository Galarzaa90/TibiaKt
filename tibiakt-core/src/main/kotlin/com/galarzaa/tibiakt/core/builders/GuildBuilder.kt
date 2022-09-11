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

@BuilderDsl
public class GuildBuilder : TibiaKtBuilder<Guild>() {
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

    public class GuildMemberBuilder : TibiaKtBuilder<GuildMember>() {
        public lateinit var rank: String
        public lateinit var name: String
        public var title: String? = null
        public lateinit var vocation: Vocation
        public var level: Int = 0
        public lateinit var joiningDate: LocalDate
        public var isOnline: Boolean = false

        override fun build(): GuildMember = GuildMember(
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
