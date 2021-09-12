package com.galarzaa.tibiakt.models

import com.galarzaa.tibiakt.core.getTibiaUrl
import java.time.LocalDateTime
import kotlin.math.floor

class Character(var name: String, var level: Int) {
    var vocation: String? = null
    var sex: String? = null
    var achievementPoints: Int? = null
    var residence: String? = null
    var lastLogin: LocalDateTime? = null
    val characters: ArrayList<OtherCharacter> = arrayListOf()
    var traded = false

    val shareRange: Pair<Int, Int>
        get() {
            val minLevel = floor((level / 3.0) * 2).toInt()
            val maxLevel = floor((level / 3.0) * 2).toInt()

            return Pair(minLevel, maxLevel)
        }

    val url: String
        get() = getUrl(name)

    companion object {
        fun getUrl(name: String) = getTibiaUrl("community", Pair("subtopic", "characters"), Pair("name", name))
    }
}

class OtherCharacter(val name: String, val world: String){
    var main = false
    var deleted = false
}