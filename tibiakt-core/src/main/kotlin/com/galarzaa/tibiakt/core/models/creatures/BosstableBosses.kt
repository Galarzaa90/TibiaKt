package com.galarzaa.tibiakt.core.models.creatures

import kotlinx.serialization.Serializable

/**
 * The boostable bosses section of Tibia.com
 *
 * @property boostedBoss The boosted boss of the day.
 * @property bosses The list of bostable bosses.
 */
@Serializable
public data class BosstableBosses(
    val boostedBoss: BossEntry,
    val bosses: List<BossEntry>,
)
