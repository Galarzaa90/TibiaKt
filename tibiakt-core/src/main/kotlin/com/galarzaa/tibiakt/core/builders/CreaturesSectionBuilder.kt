package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.creatures.CreatureEntry
import com.galarzaa.tibiakt.core.models.creatures.CreaturesSection
import com.galarzaa.tibiakt.core.utils.BuilderDsl

@BuilderDsl
public inline fun creaturesSection(block: CreaturesSectionBuilder.() -> Unit): CreaturesSection =
    CreaturesSectionBuilder().apply(block).build()

@BuilderDsl
public inline fun creaturesSectionBuilder(block: CreaturesSectionBuilder.() -> Unit): CreaturesSectionBuilder =
    CreaturesSectionBuilder().apply(block)

@BuilderDsl
public class CreaturesSectionBuilder : TibiaKtBuilder<CreaturesSection>() {
    public var boostedCreature: CreatureEntry? = null
    public val creatures: MutableList<CreatureEntry> = mutableListOf()

    public fun boostedCreature(boostedCreature: CreatureEntry): CreaturesSectionBuilder =
        apply { this.boostedCreature = boostedCreature }

    public fun boostedCreature(block: CreatureEntryBuilder.() -> Unit): CreaturesSectionBuilder =
        apply { this.boostedCreature = CreatureEntryBuilder().apply(block).build() }

    public fun addCreature(creature: CreatureEntry): CreaturesSectionBuilder = apply { creatures.add(creature) }

    @BuilderDsl
    public fun addCreature(block: CreatureEntryBuilder.() -> Unit): CreaturesSectionBuilder =
        apply { creatures.add(CreatureEntryBuilder().apply(block).build()) }

    public override fun build(): CreaturesSection = CreaturesSection(
        boostedCreature = boostedCreature
            ?: throw IllegalArgumentException("boostedCreature is required"), creatures = creatures
    )

    @BuilderDsl
    public class CreatureEntryBuilder : TibiaKtBuilder<CreatureEntry>() {
        public lateinit var name: String
        public lateinit var identifier: String

        public override fun build(): CreatureEntry = CreatureEntry(
            name = if (::name.isInitialized) name else throw IllegalArgumentException("name is required"),
            identifier = if (::identifier.isInitialized) identifier else throw IllegalArgumentException("identifier is required"),
        )
    }
}
