package com.galarzaa.tibiakt.core.models

import com.galarzaa.tibiakt.core.enums.Vocation
import kotlinx.serialization.Serializable

@Serializable
data class OnlineCharacter(
    override val name: String,
    val level: Int,
    val vocation: Vocation
) : BaseCharacter