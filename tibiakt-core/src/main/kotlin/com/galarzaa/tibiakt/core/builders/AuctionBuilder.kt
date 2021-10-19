package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.BidType
import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.*
import java.time.Instant

class AuctionBuilder {
    private var name: String? = null
    private var auctionId: Int? = null
    private var level: Int? = null
    private var world: String? = null
    private var vocation: Vocation? = null
    private var sex: String? = null
    private var outfit: DisplayOutfit? = null
    private val displayedItems: MutableList<DisplayItem> = mutableListOf()
    private val salesArguments: MutableList<SalesArgument> = mutableListOf()
    private var auctionStart: Instant? = null
    private var auctionEnd: Instant? = null
    private var bid: Int = 0
    private var bidType: BidType? = null
    private var details: AuctionDetails? = null

    fun name(name: String) = apply { this.name = name }
    fun auctionId(auctionId: Int) = apply { this.auctionId = auctionId }
    fun level(level: Int) = apply { this.level = level }
    fun vocation(vocation: Vocation) = apply { this.vocation = vocation }
    fun sex(sex: String) = apply { this.sex = sex }
    fun world(world: String) = apply { this.world = world }
    fun outfit(outfit: DisplayOutfit) = apply { this.outfit = outfit }
    fun outfit(outfitId: Int, addons: Int) = apply { outfit = DisplayOutfit(outfitId, addons) }
    fun addDisplayedItem(displayedItem: DisplayItem) = apply { displayedItems.add(displayedItem) }
    fun auctionStart(auctionStart: Instant) = apply { this.auctionStart = auctionStart }
    fun auctionEnd(auctionEnd: Instant) = apply { this.auctionEnd = auctionEnd }
    fun bid(bid: Int) = apply { this.bid = bid }
    fun bidType(bidType: BidType) = apply { this.bidType = bidType }

    fun build() = Auction(
        name = name ?: throw IllegalStateException("name is required"),
        auctionId = auctionId ?: throw IllegalStateException("aucitonId is required"),
        level = level ?: throw IllegalStateException("level is required"),
        world = world ?: throw IllegalStateException("world is required"),
        vocation = vocation ?: throw IllegalStateException("vocaiton is required"),
        sex = sex ?: throw IllegalStateException("sex is required"),
        outfit = outfit ?: throw IllegalStateException("outfit is required"),
        displayedItems = displayedItems,
        salesArguments = salesArguments,
        auctionStart = auctionStart ?: throw IllegalStateException("auctionStart is required"),
        auctionEnd = auctionEnd ?: throw IllegalStateException("auctionEnd is required"),
        bid = bid,
        bidType = bidType ?: throw IllegalStateException("bidType is required"),
        details = details,
    )
}