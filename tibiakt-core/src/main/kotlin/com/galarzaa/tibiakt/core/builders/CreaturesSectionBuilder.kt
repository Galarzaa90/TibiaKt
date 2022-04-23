package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.creatures.CreatureEntry
import com.galarzaa.tibiakt.core.models.creatures.CreaturesSection
import com.galarzaa.tibiakt.core.utils.BuilderDsl

@BuilderDsl
inline fun creaturesSection(block: CreaturesSectionBuilder.() -> Unit) = CreaturesSectionBuilder().apply(block).build()

@BuilderDsl
inline fun creaturesSectionBuilder(block: CreaturesSectionBuilder.() -> Unit) = CreaturesSectionBuilder().apply(block)

@BuilderDsl
class CreaturesSectionBuilder : TibiaKtBuilder<CreaturesSection>() {
    var boostedCreature: CreatureEntry? = null
    val creatures: MutableList<CreatureEntry> = mutableListOf()

    fun boostedCreature(boostedCreature: CreatureEntry) = apply { this.boostedCreature = boostedCreature }
    fun boostedCreature(block: CreatureEntryBuilder.() -> Unit) =
        apply { this.boostedCreature = CreatureEntryBuilder().apply(block).build() }

    fun addCreature(creature: CreatureEntry) = apply { creatures.add(creature) }

    @BuilderDsl
    fun addCreature(block: CreatureEntryBuilder.() -> Unit) =
        apply { creatures.add(CreatureEntryBuilder().apply(block).build()) }

    override fun build() = CreaturesSection(boostedCreature = boostedCreature
        ?: throw IllegalArgumentException("boostedCreature is required"), creatures = creatures)

    @BuilderDsl
    class CreatureEntryBuilder : TibiaKtBuilder<CreatureEntry>() {
        lateinit var name: String
        lateinit var identifier: String

        override fun build() = CreatureEntry(
            name = if (::name.isInitialized) name else throw IllegalArgumentException("name is required"),
            identifier = if (::identifier.isInitialized) identifier else throw IllegalArgumentException("identifier is required"),
        )
    }
}