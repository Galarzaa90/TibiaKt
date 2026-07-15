/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.tibiakt.core.section.charactertrade.bazaar.parser

import com.galarzaa.tibiakt.TestUtilities.getResource
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.Auction
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionDetails
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionStatus
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAtLeastOne
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldNotBeNull
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

    test("Auction with Echo Warden bestiary progress") {
        val echoWardenIcon = "<td><img alt=\"Echo Warden killed\"></td>"
        val content = getResource("auction/auctionWithUpgradedItems.txt").replace(
            Regex("""(<td[^>]*>\s*<img[^>]*alt\s*=\s*\"Monster Mastery (?:locked|unlocked)\"[^>]*>\s*</td>)"""),
            "$1$echoWardenIcon",
        )
        val auction = AuctionParser.fromContent(
            content,
        ).shouldBeInstanceOf<Auction>()

        auction.details.shouldBeInstanceOf<AuctionDetails>()
        auction.details.bestiaryProgress.forAtLeastOne {
            it.hasKilledEchoWarden shouldBe true
        }
    }

    test("Auction with bounty talisman") {
        val bountyTable = """
            <div class="CharacterDetailsBlock " id="BountyTalisman"><a name="Bounty Talisman"></a><div class="TopButtonContainer"><a name="Bounty Talisman"></a><div class="TopButton"><a name="Bounty Talisman"></a><a onclick="ScrollToAnchor('top');"><img style="border: 0px; max-width: 3420px;" src="https://static.tibia.com/images/global/content/back-to-top.gif"></a></div></div><div class="TableContainer">  <div class="CaptionContainer">      <div class="CaptionInnerContainer">        <span class="CaptionEdgeLeftTop" style="background-image:url(https://static.tibia.com/images/global/content/box-frame-edge.gif);"></span>        <span class="CaptionEdgeRightTop" style="background-image:url(https://static.tibia.com/images/global/content/box-frame-edge.gif);"></span>        <span class="CaptionBorderTop" style="background-image:url(https://static.tibia.com/images/global/content/table-headline-border.gif);"></span>        <span class="CaptionVerticalLeft" style="background-image:url(https://static.tibia.com/images/global/content/box-frame-vertical.gif);"></span>        <div class="Text">Bounty Talisman</div>        <span class="CaptionVerticalRight" style="background-image:url(https://static.tibia.com/images/global/content/box-frame-vertical.gif);"></span>        <span class="CaptionBorderBottom" style="background-image:url(https://static.tibia.com/images/global/content/table-headline-border.gif);"></span>        <span class="CaptionEdgeLeftBottom" style="background-image:url(https://static.tibia.com/images/global/content/box-frame-edge.gif);"></span>        <span class="CaptionEdgeRightBottom" style="background-image:url(https://static.tibia.com/images/global/content/box-frame-edge.gif);"></span>      </div>    </div><table class="Table3" cellpadding="0" cellspacing="0">        <tbody><tr>      <td>        <div class="TableScrollbarWrapper" style="width: 3410px; display: none;">          <div class="TableScrollbarContainer"> </div>        </div>        <div class="InnerTableContainer" style="max-width: 3410px; margin-bottom: 0px;">          <table style="width:100%;"><tbody><tr><td>  <div class="TableContentContainer ">    <table class="TableContent" width="100%" style="border:1px solid #faf0d7;"><tbody><tr class="Even"><td><span class="LabelV">Bounty Points:</span><div style="float:right; text-align: right;">0</div></td></tr>    </tbody></table>  </div></td></tr><tr><td>  <div class="TableContentContainer ">    <table class="TableContent" width="100%" style="border:1px solid #faf0d7;"><tbody><tr class="LabelH"><td style="width: 98%;">Effect</td><td>Level</td><td>Value</td></tr><tr class="Odd"><td style="text-align: left;">Bonus Damage Against Task Creatures</td><td style="text-align: right;">0</td><td style="text-align: right;">2.50%</td></tr><tr class="Even"><td style="text-align: left;">Life Leech From Task Creatures</td><td style="text-align: right;">0</td><td style="text-align: right;">2.50%</td></tr><tr class="Odd"><td style="text-align: left;">Bonus Loot From Task Creatures</td><td style="text-align: right;">0</td><td style="text-align: right;">2.50%</td></tr><tr class="Even"><td style="text-align: left;">Chance For Double Bestiary Progress from Task Creatures</td><td style="text-align: right;">0</td><td style="text-align: right;">5.00%</td></tr>    </tbody></table>  </div></td></tr>          </tbody></table>        </div>      </td>    </tr>  </tbody></table></div></div>
        """.trimIndent()
        val content = getResource("auction/auctionWithUpgradedItems.txt").replace("</body>", "$bountyTable</body>")
        val auction = AuctionParser.fromContent(content)

        auction.shouldBeInstanceOf<Auction>()
        val bountyTalisman = auction.details.shouldNotBeNull().bountyTalisman.shouldNotBeNull()
        bountyTalisman.points shouldBe 0
        bountyTalisman.effects.size shouldBe 4
        bountyTalisman.effects.first().effect shouldBe "Bonus Damage Against Task Creatures"
        bountyTalisman.effects.first().level shouldBe 0
        bountyTalisman.effects.first().value shouldBe 2.5
    }
})
