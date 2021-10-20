package com.galarzaa.tibiakt.core.models.character

import kotlinx.serialization.Serializable

/**
 * Represents a killer listed in a death.
 *
 * @property name The name of the killer. If the killer is a summoned creature, this is the summoner's name.
 * @property isPlayer Whether the killer is a player.
 * @property traded Whether the character was traded after this death occurred.
 * @property summon The summoned creature that caused this death, if applicable.
 */
@Serializable
data class Killer(
    val name: String,
    val isPlayer: Boolean,
    val summon: String? = null,
    val traded: Boolean = false
)