package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.models.character.BaseCharacter
import kotlinx.serialization.Serializable

@Serializable
data class LeaderboardEntry(
    val rank: Int,
    override val name: String,
    val dromeLevel: Int
) : BaseCharacter
