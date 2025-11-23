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

package com.galarzaa.tibiakt.core.section.community.house.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.builder.requireField
import com.galarzaa.tibiakt.core.section.community.house.model.House
import com.galarzaa.tibiakt.core.section.community.house.model.HouseStatus
import com.galarzaa.tibiakt.core.section.community.house.model.HouseType
import kotlin.time.Instant

@BuilderDsl
internal inline fun house(block: HouseBuilder.() -> Unit): House = HouseBuilder().apply(block).build()

@BuilderDsl
internal inline fun houseBuilder(block: HouseBuilder.() -> Unit): HouseBuilder = HouseBuilder().apply(block)


/** Builder for [House] instances. */
@BuilderDsl
internal class HouseBuilder : TibiaKtBuilder<House> {
    var houseId: Int? = null
    var name: String? = null
    var size: Int? = null
    var houseType: HouseType? = null
    var beds: Int? = null
    var rent: Int? = null
    var world: String? = null
    var status: HouseStatus? = null
    var paidUntil: Instant? = null
    var ownerName: String? = null
    var transferScheduledAt: Instant? = null
    var transferPrice: Int? = null
    var isTransferAccepted: Boolean? = null
    var transferRecipient: String? = null
    var highestBid: Int? = null
    var highestBidder: String? = null
    var auctionEndsAt: Instant? = null

    @Suppress("ComplexMethod")
    override fun build(): House = when (status) {
        HouseStatus.RENTED -> House.Rented(
            houseId = requireField(houseId, "houseId"),
            name = requireField(name, "name"),
            size = requireField(size, "size"),
            houseType = requireField(houseType, "houseType"),
            beds = requireField(beds, "beds"),
            rent = requireField(rent, "rent"),
            world = requireField(world, "world"),
            paidUntil = requireField(paidUntil, "paidUntil"),
            ownerName = requireField(ownerName, "ownerName"),
            transferScheduledAt = transferScheduledAt,
            transferPrice = transferPrice,
            isTransferAccepted = isTransferAccepted,
            transferRecipient = transferRecipient
        )

        HouseStatus.AUCTIONED -> House.Auctioned(
            houseId = requireField(houseId, "houseId"),
            name = requireField(name, "name"),
            size = requireField(size, "size"),
            houseType = requireField(houseType, "houseType"),
            beds = requireField(beds, "beds"),
            rent = requireField(rent, "rent"),
            world = requireField(world, "world"),
            highestBid = highestBid,
            highestBidder = highestBidder,
            auctionEndsAt = auctionEndsAt,
        )

        else -> error("status is required")
    }
}
