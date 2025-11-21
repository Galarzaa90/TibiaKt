/*
 * Copyright © 2025 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.galarzaa.tibiakt.core.section.library.creature.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.builder.requireField
import com.galarzaa.tibiakt.core.section.library.creature.model.BoostableBosses
import com.galarzaa.tibiakt.core.section.library.creature.model.BossEntry

@BuilderDsl
internal inline fun boostableBosses(block: BoostableBossesBuilder.() -> Unit): BoostableBosses =
    BoostableBossesBuilder().apply(block).build()

@BuilderDsl
internal inline fun boostableBossesBuilder(block: BoostableBossesBuilder.() -> Unit): BoostableBossesBuilder =
    BoostableBossesBuilder().apply(block)

/** Builder for [BoostableBosses] instances. */
@BuilderDsl
internal class BoostableBossesBuilder : TibiaKtBuilder<BoostableBosses> {
    var boostedBoss: BossEntry? = null
    val bosses: MutableList<BossEntry> = mutableListOf()

    fun boostedBoss(boostedBoss: BossEntry): BoostableBossesBuilder = apply { this.boostedBoss = boostedBoss }

    @BuilderDsl
    fun boostedBoss(block: BossEntryBuilder.() -> Unit): BoostableBossesBuilder =
        apply { this.boostedBoss = BossEntryBuilder().apply(block).build() }

    fun addCreature(creature: BossEntry): BoostableBossesBuilder = apply { bosses.add(creature) }

    @BuilderDsl
    fun addCreature(block: BossEntryBuilder.() -> Unit): BoostableBossesBuilder =
        apply { bosses.add(BossEntryBuilder().apply(block).build()) }

    override fun build(): BoostableBosses = BoostableBosses(
        boostedBoss = requireField(boostedBoss, "boostedBoss"),
        bosses = bosses
    )

    @BuilderDsl
    class BossEntryBuilder : TibiaKtBuilder<BossEntry> {
        lateinit var name: String
        lateinit var identifier: String

        override fun build(): BossEntry = BossEntry(
            name = requireField(::name.isInitialized, "name") { name },
            identifier = requireField(::identifier.isInitialized, "identifier") { identifier },
        )
    }
}
