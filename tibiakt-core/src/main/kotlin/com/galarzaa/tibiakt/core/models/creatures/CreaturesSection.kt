package com.galarzaa.tibiakt.core.models.creatures

import com.galarzaa.tibiakt.core.utils.getCreaturesSectionUrl
import kotlinx.serialization.Serializable

@Serializable
data class CreaturesSection(
    val boostedCreature: CreatureEntry,
    val creatures: List<CreatureEntry>,
) {
    val url get() = getCreaturesSectionUrl()
}


