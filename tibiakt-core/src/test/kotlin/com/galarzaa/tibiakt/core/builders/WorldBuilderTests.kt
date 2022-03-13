package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.enums.Vocation
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import java.time.Instant
import java.time.YearMonth

class WorldBuilderTests : StringSpec({
    "Build with all required parameters"{
        val world = world {
            name = "Gladera"
            isOnline = true
            location = "North America"
            pvpType = PvpType.OPTIONAL_PVP
            onlineRecordDateTime = Instant.now()
            onlineRecordCount = 425
            creationDate = YearMonth.of(2018, 4)
            onlinePlayers {
                onlinePlayer("Someone", 123, Vocation.DRUID)
                onlinePlayer("Galarzaa Fidera", 285, Vocation.ROYAL_PALADIN)
                player {
                    name = "Tschas"
                    level = 123
                    vocation = Vocation.ELDER_DRUID
                }
            }
        }

        world.playersOnline shouldHaveSize 3
    }
})