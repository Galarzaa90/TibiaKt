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


package com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model

import com.galarzaa.tibiakt.core.domain.character.Sex
import com.galarzaa.tibiakt.core.domain.character.Vocation
import com.galarzaa.tibiakt.core.section.charactertrade.urls.auctionUrl
import com.galarzaa.tibiakt.core.section.community.urls.characterUrl
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * An auction in the [CharacterBazaar].
 *
 * @property name The name of the character on auction.
 * @property auctionId The ID of the auction.
 * @property level The level of the character.
 * @property world The world of the character.
 * @property vocation The vocation of the character.
 * @property sex The character's sex.
 * @property outfit The current outfit the character is wearing.
 * @property displayedItems A list of items selected for display.
 * @property salesArguments Arguments selected by the auction author.
 * @property startsAt The date when the auction started.
 * @property endsAt The date when the auction ends.
 * @property bid The current bid on the auction.
 * @property bidType The type of bid.
 * @property status The current status of the auction.
 * @property details The details of the auction.
 */
@Serializable
public data class Auction(
    val name: String,
    val auctionId: Int,
    val level: Int,
    val world: String,
    val vocation: Vocation,
    val sex: Sex,
    val outfit: OutfitImage,
    val displayedItems: List<ItemEntry>,
    val salesArguments: List<SalesArgument>,
    val startsAt: Instant,
    val endsAt: Instant,
    val bid: Int,
    val bidType: BidType,
    val status: AuctionStatus,
    val details: AuctionDetails?,
) {
    /** URL to the auction. */
    val url: String get() = auctionUrl(auctionId)

    /** URL to the character being sold. */
    val characterUrl: String get() = characterUrl(name)
}
