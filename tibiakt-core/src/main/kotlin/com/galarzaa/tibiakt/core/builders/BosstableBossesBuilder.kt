package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.creatures.BossEntry
import com.galarzaa.tibiakt.core.models.creatures.BosstableBosses
import com.galarzaa.tibiakt.core.utils.BuilderDsl

@BuilderDsl
inline fun bosstableBosses(block: BosstableBossesBuilder.() -> Unit) = BosstableBossesBuilder().apply(block).build()

@BuilderDsl
inline fun bosstableBossesBuilder(block: BosstableBossesBuilder.() -> Unit) = BosstableBossesBuilder().apply(block)

@BuilderDsl
class BosstableBossesBuilder : TibiaKtBuilder<BosstableBosses>() {
    var boostedBoss: BossEntry? = null
    val bosses: MutableList<BossEntry> = mutableListOf()

    fun boostedBoss(boostedBoss: BossEntry) = apply { this.boostedBoss = boostedBoss }

    @BuilderDsl
    fun boostedBoss(block: BossEntryBuilder.() -> Unit) =
        apply { this.boostedBoss = BossEntryBuilder().apply(block).build() }

    fun addCreature(creature: BossEntry) = apply { bosses.add(creature) }

    @BuilderDsl
    fun addCreature(block: BossEntryBuilder.() -> Unit) = apply { bosses.add(BossEntryBuilder().apply(block).build()) }

    override fun build() = BosstableBosses(
        boostedBoss = boostedBoss ?: throw IllegalArgumentException("boostedBoss is required"), bosses = bosses
    )

    @BuilderDsl
    class BossEntryBuilder : TibiaKtBuilder<BossEntry>() {
        lateinit var name: String
        lateinit var identifier: String

        override fun build() = BossEntry(
            name = if (::name.isInitialized) name else throw IllegalArgumentException("name is required"),
            identifier = if (::identifier.isInitialized) identifier else throw IllegalArgumentException("identifier is required"),
        )
    }
}