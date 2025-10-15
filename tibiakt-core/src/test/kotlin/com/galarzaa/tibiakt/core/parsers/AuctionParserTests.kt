/*
 * Copyright © 2023 Allan Galarza
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

import com.galarzaa.tibiakt.TestUtilities.getResource
import com.galarzaa.tibiakt.core.enums.AuctionStatus
import com.galarzaa.tibiakt.core.models.bazaar.Auction
import com.galarzaa.tibiakt.core.models.bazaar.AuctionDetails
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAtLeastOne
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class AuctionParserTests : FunSpec({
    test("Finished auction") {
        val auction = AuctionParser.fromContent(getResource("auction/auctionFinished.txt"))

        auction.shouldBeInstanceOf<Auction>()
        auction.status shouldBe AuctionStatus.FINISHED
    }

    test("Auction not found") {
        val auction = AuctionParser.fromContent(getResource("auction/auctionNotFound.txt"))

        auction shouldBe null
    }

    test("Auction with upgraded items") {
        val auction = AuctionParser.fromContent(getResource("auction/auctionWithUpgradedItems.txt"))

        auction.shouldBeInstanceOf<Auction>()
        auction.details.shouldBeInstanceOf<AuctionDetails>()
        auction.displayedItems.forAtLeastOne {
            it.tier shouldBeGreaterThan 0
        }
    }
})
