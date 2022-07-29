package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.enums.Vocation
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import kotlinx.datetime.Clock
import java.time.YearMonth

class WorldBuilderTests : StringSpec({
    "Build with all required parameters"{
        val world = world {
            name = "Gladera"
            isOnline = true
            location = "North America"
            pvpType = PvpType.OPTIONAL_PVP
            onlineRecordDateTime = Clock.System.now()
            onlineRecordCount = 425
            creationDate = YearMonth.of(2018, 4)
            addOnlinePlayer("Someone", 123, Vocation.DRUID)
            addOnlinePlayer("Galarzaa Fidera", 285, Vocation.ROYAL_PALADIN)
        }

        world.playersOnline shouldHaveSize 2
    }
})