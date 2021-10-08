package com.galarzaa.tibiakt.models

import kotlinx.serialization.Serializable

@Serializable
data class LeaderboardEntry(
    val rank: Int,
    override val name: String,
    val dromeLevel: Int
) : BaseCharacter
