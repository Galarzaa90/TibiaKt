package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class HouseParserTests : StringSpec({
    "Parse a rented house"{
        val house = HouseParser.fromContent(getResource("houses/houseRented.txt"))
        house shouldNotBe null
        house!!.houseId shouldBe 40501
        house.name shouldBe "Coastwood 1"
        house.size shouldBe 16
        house.beds shouldBe 2
        house.rent shouldBe 50_000
        house.type shouldBe HouseType.HOUSE
        house.world shouldBe "Alumbra"
        house.status shouldBe HouseStatus.RENTED
        house.paidUntil shouldNotBe null
    }

    "Parse a guildhall on auction with bids"{
        val house = HouseParser.fromContent(getResource("houses/houseAuctionedBidsGuildhall.txt"))
        house shouldNotBe null
        house!!.houseId shouldBe 20002
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

    "Parse a house set to be transferred but not accepted"{
        val house = HouseParser.fromContent(getResource("houses/houseTransferNotAccepted.txt"))
        house shouldNotBe null
        house!!.houseId shouldBe 59048
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

    "Parse a house set to be transferred, and accepted"{
        val house = HouseParser.fromContent(getResource("houses/houseTransferAccepted.txt"))
        house shouldNotBe null
        house!!.houseId shouldBe 37016
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
})