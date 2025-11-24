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

package com.galarzaa.tibiakt.core.parser

import com.galarzaa.tibiakt.TestUtilities.getResource
import com.galarzaa.tibiakt.core.section.community.house.model.House
import com.galarzaa.tibiakt.core.section.community.house.model.HouseStatus
import com.galarzaa.tibiakt.core.section.community.house.parser.HouseParser
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

class HouseParserTests : FunSpec({
    test("House rented"){
        val house = HouseParser.fromContent(getResource("house/houseRented.txt"))

        house.shouldBeInstanceOf<House.Rented>()
        with(house){
            status shouldBe HouseStatus.RENTED
            rent shouldNotBe null
            paidUntil shouldNotBe null
            transferScheduledAt shouldBe null
            transferPrice shouldBe null
            transferIsAccepted shouldBe null
            transferRecipient shouldBe null
        }
    }
    test("House rented, being transferred, accepted") {
        val house = HouseParser.fromContent(getResource("house/houseRentedAcceptedTransfer.txt"))

        house.shouldBeInstanceOf<House.Rented>()
        with(house) {
            status shouldBe HouseStatus.RENTED
            rent shouldNotBe null
            paidUntil shouldNotBe null
            transferScheduledAt shouldNotBe null
            transferPrice shouldNotBe null
            transferIsAccepted shouldBe true
            transferRecipient shouldNotBe null
        }
    }
    test("House auctioned with bids") {
        val house = HouseParser.fromContent(getResource("house/houseAuctionedWithBids.txt"))

        house.shouldBeInstanceOf<House.Auctioned>()

        with(house) {
            status shouldBe HouseStatus.AUCTIONED
            highestBidder shouldNotBe null
            highestBid shouldNotBe null
            highestBidderUrl shouldNotBe null
            auctionEndsAt shouldNotBe null
        }
    }
    test("House auctioned without bids") {
        val house = HouseParser.fromContent(getResource("house/houseAuctionedWithoutBids.txt"))

        house.shouldBeInstanceOf<House.Auctioned>()

        with(house) {
            status shouldBe HouseStatus.AUCTIONED
            highestBidder shouldBe null
            highestBid shouldBe null
            highestBidderUrl shouldBe null
            auctionEndsAt shouldBe null
        }
    }
    test("House not found"){
        val house = HouseParser.fromContent(getResource("house/houseNotFound.txt"))

        house shouldBe null
    }
})
