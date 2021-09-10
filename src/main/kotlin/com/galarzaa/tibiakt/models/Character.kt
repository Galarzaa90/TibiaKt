package com.galarzaa.tibiakt.models

import java.time.LocalDateTime
import kotlin.math.floor


class Character(val name: String, val level: Int) {
    var vocation: String? = null
    var sex: String? = null
    var achievementPoints: Int? = null
    var residence: String? = null
    var lastLogin: LocalDateTime? = null
    val characters: ArrayList<OtherCharacter> = arrayListOf()

    val shareRange: Pair<Int, Int>
        get() {
            val minLevel = floor((this.level / 3.0) * 2).toInt()
            val maxLevel = floor((this.level / 3.0) * 2).toInt()

            return Pair(minLevel, maxLevel)
        }
}

class OtherCharacter(val name: String, val world: String){
    var main = false
    var deleted = false
}