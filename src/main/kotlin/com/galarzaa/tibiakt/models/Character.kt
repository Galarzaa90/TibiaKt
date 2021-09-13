package com.galarzaa.tibiakt.models

import com.galarzaa.tibiakt.InstantSerializer
import com.galarzaa.tibiakt.LocalDateSerializer
import com.galarzaa.tibiakt.core.getTibiaUrl
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.LocalDate
import kotlin.math.ceil
import kotlin.math.floor


@Serializable
data class Character(
    val name: String,
    val level: Int,
    val vocation: String,
    val sex: String,
    val world: String,
    val achievementPoints: Int = 0,
    val residence: String,
    @Serializable(with = InstantSerializer::class) val lastLogin: Instant? = null,
    val recentlyTraded: Boolean = false,
    @Serializable(with = InstantSerializer::class) val deletionDate: Instant? = null,
    val formerNames: List<String> = listOf(),
    val formerWorld: String? = null,
    val characters: List<OtherCharacter> = listOf(),
) {

    val shareRange: Pair<Int, Int>
        get() {
            val minLevel = floor((level / 3.0) * 2).toInt()
            val maxLevel = ceil((level / 2.0) * 3).toInt() + if (level % 2 == 0) 1 else 0
            return Pair(minLevel, maxLevel)
        }

    val url: String
        get() = getUrl(name)

    val scheduledForDeletion: Boolean
        get() = deletionDate != null

    companion object {
        fun getUrl(name: String) = getTibiaUrl("community", Pair("subtopic", "characters"), Pair("name", name))
    }

    class Builder {
        private var name: String? = null
        private var level: Int = 2
        private var residence: String? = null
        private var vocation: String? = null
        private var sex: String? = null
        private var world: String? = null
        private var achievementPoints: Int = 0
        private var lastLogin: Instant? = null
        private var recentlyTraded: Boolean = false
        private var formerNames: List<String> = listOf()
        private var deletionDate: Instant? = null
        private var formerWorld: String? = null

        fun name(name: String) = apply { this.name = name }
        fun vocation(vocation: String) = apply { this.vocation = vocation }
        fun level(level: Int) = apply { this.level = level }
        fun sex(sex: String) = apply { this.sex = sex }
        fun world(world: String) = apply { this.world = world }
        fun achievementPoints(achievementPoints: Int) = apply { this.achievementPoints = achievementPoints }
        fun residence(residence: String) = apply { this.residence = residence }
        fun recentlyTraded(recentlyTraded: Boolean) = apply { this.recentlyTraded = recentlyTraded }
        fun lastLogin(lastLogin: Instant?) = apply { this.lastLogin = lastLogin }
        fun deletionDate(deletionDate: Instant?) = apply { this.deletionDate = deletionDate }
        fun formerNames(formerNames: List<String>) = apply { this.formerNames = formerNames }
        fun formerWorld(formerWorld: String?) = apply { this.formerWorld = formerWorld }

        fun build() =
            Character(
                name!!,
                level,
                vocation!!,
                sex!!,
                world!!,
                achievementPoints,
                residence!!,
                lastLogin,
                recentlyTraded,
                deletionDate,
                formerNames,
                formerWorld
            )
    }
}

@Serializable
data class OtherCharacter(val name: String, val world: String, val main: Boolean = false, val deleted: Boolean = false)


@Serializable
data class CharacterHouse(
    val name: String,
    val houseId: Int,
    val town: String,
    @Serializable(with = LocalDateSerializer::class) val paidUntil: LocalDate
)

@Serializable
data class GuildMembership(val guildName: String, val guildRank: String)