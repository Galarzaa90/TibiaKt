/*
 * Copyright © 2022 Allan Galarza
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

package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.creatures.BossEntry
import com.galarzaa.tibiakt.core.models.creatures.BosstableBosses
import com.galarzaa.tibiakt.core.utils.BuilderDsl

@BuilderDsl
public inline fun bosstableBosses(block: BosstableBossesBuilder.() -> Unit): BosstableBosses =
    BosstableBossesBuilder().apply(block).build()

@BuilderDsl
public inline fun bosstableBossesBuilder(block: BosstableBossesBuilder.() -> Unit): BosstableBossesBuilder =
    BosstableBossesBuilder().apply(block)

@BuilderDsl
public class BosstableBossesBuilder : TibiaKtBuilder<BosstableBosses> {
    public var boostedBoss: BossEntry? = null
    public val bosses: MutableList<BossEntry> = mutableListOf()

    public fun boostedBoss(boostedBoss: BossEntry): BosstableBossesBuilder = apply { this.boostedBoss = boostedBoss }

    @BuilderDsl
    public fun boostedBoss(block: BossEntryBuilder.() -> Unit): BosstableBossesBuilder =
        apply { this.boostedBoss = BossEntryBuilder().apply(block).build() }

    public fun addCreature(creature: BossEntry): BosstableBossesBuilder = apply { bosses.add(creature) }

    @BuilderDsl
    public fun addCreature(block: BossEntryBuilder.() -> Unit): BosstableBossesBuilder =
        apply { bosses.add(BossEntryBuilder().apply(block).build()) }

    override fun build(): BosstableBosses = BosstableBosses(
        boostedBoss = boostedBoss ?: throw IllegalArgumentException("boostedBoss is required"), bosses = bosses
    )

    @BuilderDsl
    public class BossEntryBuilder : TibiaKtBuilder<BossEntry> {
        public lateinit var name: String
        public lateinit var identifier: String

        override fun build(): BossEntry = BossEntry(
            name = if (::name.isInitialized) name else error("name is required"),
            identifier = if (::identifier.isInitialized) identifier else error("identifier is required"),
        )
    }
}
