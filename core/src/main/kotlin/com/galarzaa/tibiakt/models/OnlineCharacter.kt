package com.galarzaa.tibiakt.models

import com.galarzaa.tibiakt.enums.Vocation
import kotlinx.serialization.Serializable

@Serializable
data class OnlineCharacter(
    override val name: String,
    val level: Int,
    val vocation: Vocation
) : BaseCharacter