package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class AuctionParserTests : StringSpec({
    "Parsing an auction's details"{
        val auction = AuctionParser.fromContent(getResource("auctions/auctionDetail.txt"))
        auction shouldNotBe null
        auction!!.name shouldBe "Vinicim Defender"
        auction.world shouldBe "Nossobra"
        auction.level shouldBe 560
        auction.details shouldNotBe null
        with(auction.details!!) {
            hitPoints shouldBe 2_945
            mana shouldBe 16_650
            capacity shouldBe 5_990
            speed shouldBe 669
            skills.magicLevel shouldBe 103.1856
            experience shouldBe 2_896_070_948
            gold shouldBe 24_652
        }
    }

    "Parsing auction not found page"{
        val auction = AuctionParser.fromContent(getResource("auctions/auctionNotFound.txt"))
        auction shouldBe null
    }
})