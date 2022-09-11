package com.galarzaa.tibiakt.core.models.creatures

import kotlinx.serialization.Serializable

/**
 * A boss in the [BosstableBosses] section of Tibia.com
 *
 * @property name The name of the boss.
 * @property identifier The internal name of the boss. Used for images.
 */
@Serializable
public data class BossEntry(
    override val name: String,
    override val identifier: String,
) : BaseCreatureEntry
