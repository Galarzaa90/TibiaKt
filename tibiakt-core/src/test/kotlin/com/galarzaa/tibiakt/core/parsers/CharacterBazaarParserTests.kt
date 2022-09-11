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

package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import com.galarzaa.tibiakt.core.enums.AuctionStatus
import com.galarzaa.tibiakt.core.enums.BazaarType
import com.galarzaa.tibiakt.core.enums.BidType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class CharacterBazaarParserTests : StringSpec({
    "Parse current auctions"{
        val bazaar = CharacterBazaarParser.fromContent(getResource("bazaar/currentAuctionsDefault.txt"))
        bazaar.entries shouldHaveSize 25
        bazaar.type shouldBe BazaarType.CURRENT
        bazaar.totalPages shouldBe 127
        bazaar.resultsCount shouldBe 3155
        bazaar.entries.first().run {
            name shouldBe "White Kalashnikov"
            auctionId shouldBe 663323
            level shouldBe 528
            world shouldBe "Pacembra"
            bid shouldBe 2_888
            bidType shouldBe BidType.MINIMUM
            status shouldBe AuctionStatus.IN_PROGRESS
            outfit shouldNotBe null
            outfit.outfitId shouldBe 152
            outfit.addons shouldBe 3
        }
    }

    "Parse auction history with filters applied"{
        val bazaar = CharacterBazaarParser.fromContent(getResource("bazaar/historyWithFilters.txt"))
        bazaar.entries shouldHaveSize 25
        bazaar.filters.world shouldBe "Antica"
        bazaar.filters.minimumLevel shouldBe 1
        bazaar.filters.maximumLevel shouldBe 500
        bazaar.type shouldBe BazaarType.HISTORY
        bazaar.currentPage shouldBe 3
        bazaar.totalPages shouldBe 5
        bazaar.resultsCount shouldBe 125
        bazaar.entries.first().run {
            name shouldBe "Healing ohara"
            auctionId shouldBe 647142
            level shouldBe 393
            world shouldBe "Antica"
            bid shouldBe 3700
            bidType shouldBe BidType.WINNING
            status shouldBe AuctionStatus.FINISHED
            outfit shouldNotBe null
            outfit.outfitId shouldBe 158
            outfit.addons shouldBe 3
        }
    }
})
