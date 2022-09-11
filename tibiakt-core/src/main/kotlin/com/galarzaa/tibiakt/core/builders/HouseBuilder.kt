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

package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.models.house.House
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import kotlinx.datetime.Instant

@BuilderDsl
public inline fun house(block: HouseBuilder.() -> Unit): House = HouseBuilder().apply(block).build()

@BuilderDsl
public inline fun houseBuilder(block: HouseBuilder.() -> Unit): HouseBuilder = HouseBuilder().apply(block)


/** Builder for [House] instances. */
@BuilderDsl
public class HouseBuilder : TibiaKtBuilder<House> {
    public var houseId: Int? = null
    public var name: String? = null
    public var size: Int? = null
    public var type: HouseType? = null
    public var beds: Int? = null
    public var rent: Int? = null
    public var world: String? = null
    public var status: HouseStatus? = null
    public var paidUntil: Instant? = null
    public var owner: String? = null
    public var movingDate: Instant? = null
    public var transferPrice: Int? = null
    public var transferAccepted: Boolean? = null
    public var transferRecipient: String? = null
    public var highestBid: Int? = null
    public var highestBidder: String? = null
    public var auctionEnd: Instant? = null

    override fun build(): House = House(
        houseId = houseId ?: error("houseId is required"),
        name = name ?: error("name is required"),
        size = size ?: error("size is required"),
        type = type ?: error("type is required"),
        beds = beds ?: error("beds is required"),
        rent = rent ?: error("rent is required"),
        world = world ?: error("world is required"),
        status = status ?: error("status is required"),
        paidUntil = paidUntil,
        owner = owner,
        movingDate = movingDate,
        transferPrice = transferPrice,
        transferAccepted = transferAccepted,
        transferRecipient = transferRecipient,
        highestBid = highestBid,
        highestBidder = highestBidder,
        auctionEnd = auctionEnd,
    )
}
