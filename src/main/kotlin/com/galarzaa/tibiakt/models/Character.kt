package com.galarzaa.tibiakt.models

import com.galarzaa.tibiakt.InstantSerializer
import com.galarzaa.tibiakt.core.getTibiaUrl
import kotlinx.serialization.Serializable
import java.time.Instant
import kotlin.math.ceil
import kotlin.math.floor


@Serializable
data class Character(
    var name: String,
    var level: Int,
    var vocation: String,
    var sex: String,
    var world: String,
    var achievementPoints: Int = 0,
    var residence: String,
    @Serializable(with = InstantSerializer::class) var lastLogin: Instant? = null,
    var recentlyTraded: Boolean = false,
    @Serializable(with = InstantSerializer::class) var deletionDate: Instant? = null,
    val formerNames: MutableList<String> = mutableListOf(),
    val characters: MutableList<OtherCharacter> = mutableListOf(),
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
        var name: String? = null
        var level: Int = 2
        var residence: String? = null
        var vocation: String? = null
        var sex: String? = null
        var world: String? = null
        var achievementPoints: Int = 0
        var lastLogin: Instant? = null
        var recentlyTraded: Boolean = false
        var formerNames: MutableList<String> = mutableListOf()
        var deletionDate: Instant? = null

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
        fun formerNames(formerNames: MutableList<String>) = apply { this.formerNames = formerNames }

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
                formerNames
            )
    }
}

@Serializable
class OtherCharacter(val name: String, val world: String) {
    var main = false
    var deleted = false

    constructor(name: String, world: String, main: Boolean, deleted: Boolean) : this(name, world) {
        this.main = main
        this.deleted = deleted
    }
}