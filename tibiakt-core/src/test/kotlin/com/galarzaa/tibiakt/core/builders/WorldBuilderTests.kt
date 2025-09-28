/*
 * Copyright © 2022 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.enums.Vocation
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import kotlin.time.Clock
import kotlinx.datetime.YearMonth

class WorldBuilderTests : StringSpec({
    "Build with all required parameters"{
        val world = world {
            name = "Gladera"
            isOnline = true
            location = "North America"
            pvpType = PvpType.OPTIONAL_PVP
            onlineRecordDateTime = Clock.System.now()
            onlineRecordCount = 425
            creationDate = YearMonth(2018, 4)
            addOnlinePlayer("Someone", 123, Vocation.DRUID)
            addOnlinePlayer("Galarzaa Fidera", 285, Vocation.ROYAL_PALADIN)
        }

        world.onlinePlayers shouldHaveSize 2
    }
})
