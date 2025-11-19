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

package com.galarzaa.tibiakt.core.section.community.house.model

import com.galarzaa.tibiakt.core.domain.house.BaseHouse
import com.galarzaa.tibiakt.core.section.community.urls.characterUrl
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * A house or guildhall.
 *
 * @property name The name of the house.
 * @property size The size of the house in square meters (tiles).
 * @property houseType The type of the house.
 * @property beds The maximum amount of beds that can be placed in there.
 * @property rent The monthly rent paid for this house in gold coins.
 * @property status The current status of the house.
 */
@Serializable
public sealed class House : BaseHouse {
    abstract override val houseId: Int
    public abstract val name: String
    public abstract val size: Int
    public abstract val houseType: HouseType
    public abstract val beds: Int
    public abstract val rent: Int
    abstract override val world: String
    public abstract val status: HouseStatus

    /**
     * A rented house or guildhall.
     *
     * @property paidUntil The date when the last paid rent is due.
     * @property ownerName The character that currently owns the house.
     * @property transferScheduledAt The date when the current owner will move out of the house.
     * @property transferPrice The amount of gold coins to be paid for transferring the house.
     * @property isTransferAccepted Whether the transfer has been accepted by the recipient or not.
     * @property transferRecipient The character that will receive the house.
     */
    @Serializable
    @SerialName("RENTED")
    public data class Rented(
        override val houseId: Int,
        override val name: String,
        override val size: Int,
        override val houseType: HouseType,
        override val beds: Int,
        override val rent: Int,
        override val world: String,
        val paidUntil: Instant,
        val ownerName: String,
        val transferScheduledAt: Instant?,
        val transferPrice: Int?,
        val isTransferAccepted: Boolean?,
        val transferRecipient: String?,
    ) : House() {
        override val status: HouseStatus = HouseStatus.RENTED

        /**
         * URL to the owner's information page.
         */
        val ownerUrl: String get() = characterUrl(ownerName)

        /** URL to the transfer recipient's information page, if any. */
        val transferRecipientUrl: String? get() = transferRecipient?.let { characterUrl(it) }
    }

    /**
     * An auctioned house or guildhall.
     *
     * @property highestBid If the house is on auction, the highest bid received, if any.
     * @property highestBidder The character that placed the highest bid.
     * @property auctionEndsAt The date and time when the auction will end.
     */
    @Serializable
    @SerialName("AUCTIONED")
    public data class Auctioned(
        override val houseId: Int,
        override val name: String,
        override val size: Int,
        override val houseType: HouseType,
        override val beds: Int,
        override val rent: Int,
        override val world: String,
        val highestBid: Int?,
        val highestBidder: String?,
        val auctionEndsAt: Instant?,
    ) : House() {
        override val status: HouseStatus = HouseStatus.AUCTIONED

        /**
         * The URL to the information page of the highest bidder, if any.
         */
        val highestBidderUrl: String? get() = highestBidder?.let { characterUrl(it) }
    }
}
