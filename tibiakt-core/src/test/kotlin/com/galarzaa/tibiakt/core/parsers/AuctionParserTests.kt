package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class AuctionParserTests : StringSpec({
    "Parsing an auction's details"{
        val auction = AuctionParser.fromContent(getResource("auctions/auctionDetail.txt"))
        auction shouldNotBe null
        auction!!.name shouldBe "Bubhalo"
        auction.world shouldBe "Antica"
        auction.level shouldBe 419
        auction.details shouldNotBe null
        auction.details!!.run {
            hitPoints shouldBe 2240
            mana shouldBe 12420
            capacity shouldBe 4580
            speed shouldBe 528
            skills.magicLevel shouldBe 99.4142
            experience shouldBe 1216886752
            gold shouldBe 267387

        }
    }

    "Parsing auction not found page"{
        val auction = AuctionParser.fromContent(getResource("auctions/auctionNotFound.txt"))
        auction shouldBe null
    }
})