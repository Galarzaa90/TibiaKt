package com.galarzaa.tibiakt.models

import com.galarzaa.tibiakt.core.getTibiaUrl
import java.time.Instant
import kotlin.math.ceil
import kotlin.math.floor

open class BaseCharacter(open var name: String) {
    val url: String
        get() = getUrl(name)

    companion object {
        fun getUrl(name: String) = getTibiaUrl("community", Pair("subtopic", "characters"), Pair("name", name))
    }
}

data class Character(
    override var name: String,
    var level: Int,
    var vocation: String,
    var sex: String,
    var world: String,
    var achievementPoints: Int = 0,
    var residence: String,
    var lastLogin: Instant? = null,
    var traded: Boolean = false,
    val characters: ArrayList<OtherCharacter> = arrayListOf(),
) : BaseCharacter(name) {

    val shareRange: Pair<Int, Int>
        get() {
            val minLevel = floor((level / 3.0) * 2).toInt()
            val maxLevel = ceil((level / 2.0) * 3).toInt() + if (level % 2 == 0) 1 else 0
            return Pair(minLevel, maxLevel)
        }

    companion object {
        fun getUrl(name: String) = BaseCharacter.getUrl(name)
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
        var traded: Boolean = false

        fun name(name: String) = apply { this.name = name }
        fun vocation(vocation: String) = apply { this.vocation = vocation }
        fun level(level: Int) = apply { this.level = level }
        fun sex(sex: String) = apply { this.sex = sex }
        fun world(world: String) = apply { this.world = world }
        fun achievementPoints(achievementPoints: Int) = apply { this.achievementPoints = achievementPoints }
        fun residence(residence: String) = apply { this.residence = residence }
        fun traded(traded: Boolean) = apply { this.traded = traded }
        fun lastLogin(lastLogin: Instant?) = apply { this.lastLogin = lastLogin }

        fun build() =
            Character(name!!, level, vocation!!, sex!!, world!!, achievementPoints, residence!!, lastLogin, traded)
    }
}

class OtherCharacter(val name: String, val world: String) {
    var main = false
    var deleted = false

    constructor(name: String, world: String, main: Boolean, deleted: Boolean) : this(name, world) {
        this.main = main
        this.deleted = deleted
    }
}