package com.galarzaa.tibiakt.models

import kotlinx.serialization.Serializable

@Serializable
data class OnlineCharacter(
    override val name: String,
    val level: Int,
    val vocation: String
) : BaseCharacter