package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.creatures.CreatureEntry
import com.galarzaa.tibiakt.core.models.creatures.CreaturesSection

class CreaturesSectionBuilder {
    var boostedCreature: CreatureEntry? = null
    val creatures: MutableList<CreatureEntry> = mutableListOf()

    fun boostedCreature(boostedCreature: CreatureEntry) = apply { this.boostedCreature = boostedCreature }
    fun addCreature(creature: CreatureEntry) = apply { creatures.add(creature) }

    fun build() = CreaturesSection(
        boostedCreature = boostedCreature ?: throw IllegalArgumentException("boostedCreature is required"),
        creatures = creatures
    )
}