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
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class HouseParserTests : StringSpec({
    "Rented house"{
        val house = HouseParser.fromContent(getResource("houses/houseRented.txt"))
        house shouldNotBe null
        house!!.houseId shouldBe 40_501
        house.name shouldBe "Coastwood 1"
        house.size shouldBe 16
        house.beds shouldBe 2
        house.rent shouldBe 50_000
        house.type shouldBe HouseType.HOUSE
        house.world shouldBe "Alumbra"
        house.status shouldBe HouseStatus.RENTED
        house.paidUntil shouldNotBe null
    }

    "Guildhall on auction with bids"{
        val house = HouseParser.fromContent(getResource("houses/houseAuctionedBidsGuildhall.txt"))
        house shouldNotBe null
        house!!.houseId shouldBe 20_002
        house.name shouldBe "House of Recreation"
        house.size shouldBe 401
        house.beds shouldBe 16
        house.rent shouldBe 500_000
        house.type shouldBe HouseType.GUILDHALL
        house.world shouldBe "Ferobra"
        house.status shouldBe HouseStatus.AUCTIONED
        house.paidUntil shouldBe null
        house.auctionEnd shouldNotBe null
        house.highestBidder shouldBe "Eiike"
        house.highestBid shouldBe 0
    }

    "House set to be transferred but not accepted"{
        val house = HouseParser.fromContent(getResource("houses/houseTransferNotAccepted.txt"))
        house shouldNotBe null
        house!!.houseId shouldBe 59_048
        house.name shouldBe "Unklath II b"
        house.size shouldBe 17
        house.beds shouldBe 1
        house.rent shouldBe 50_000
        house.type shouldBe HouseType.HOUSE
        house.world shouldBe "Monza"
        house.owner shouldBe "Grandpa Asan"
        house.status shouldBe HouseStatus.RENTED
        house.paidUntil shouldNotBe null
        house.movingDate shouldNotBe null
        house.transferAccepted shouldNotBe true
        house.transferRecipient shouldBe "Szatanku"
        house.transferPrice shouldBe 10
    }

    "House set to be transferred, and accepted"{
        val house = HouseParser.fromContent(getResource("houses/houseTransferAccepted.txt"))
        house shouldNotBe null
        house!!.houseId shouldBe 37_016
        house.name shouldBe "Radiant Plaza 4"
        house.size shouldBe 186
        house.beds shouldBe 3
        house.rent shouldBe 800_000
        house.type shouldBe HouseType.HOUSE
        house.world shouldBe "Ferobra"
        house.owner shouldBe "Valeth Ossa"
        house.status shouldBe HouseStatus.RENTED
        house.paidUntil shouldNotBe null
        house.movingDate shouldNotBe null
        house.transferAccepted shouldBe true
        house.transferRecipient shouldBe "King Brunno"
        house.transferPrice shouldBe 1
    }

    "House not found" {
        val house = HouseParser.fromContent(getResource("houses/houseNotFound.txt"))
        house shouldBe null
    }
})
