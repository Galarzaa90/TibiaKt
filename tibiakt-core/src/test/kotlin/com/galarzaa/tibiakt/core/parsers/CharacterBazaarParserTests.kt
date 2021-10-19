package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.TestResources.getResource
import com.galarzaa.tibiakt.core.enums.BidType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class CharacterBazaarParserTests : StringSpec({
    "Parse current auctions"{
        val bazaar = CharacterBazaarParser.fromContent(getResource("bazaar/currentAuctionsDefault.txt"))
        bazaar.entries shouldHaveSize 25
        bazaar.entries.first().run {
            name shouldBe "White Kalashnikov"
            auctionId shouldBe 663323
            level shouldBe 528
            world shouldBe "Pacembra"
            bid shouldBe 2_888
            bidType shouldBe BidType.MINIMUM
            outfit shouldNotBe null
            outfit.outfitId shouldBe 152
            outfit.addons shouldBe 3
        }
    }
}) {
}