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
import com.galarzaa.tibiakt.core.section.library.creature.model.CreatureEntry
import com.galarzaa.tibiakt.core.section.library.creature.model.CreaturesSection

@BuilderDsl
internal inline fun creaturesSection(block: CreaturesSectionBuilder.() -> Unit): CreaturesSection =
    CreaturesSectionBuilder().apply(block).build()

@BuilderDsl
internal inline fun creaturesSectionBuilder(block: CreaturesSectionBuilder.() -> Unit): CreaturesSectionBuilder =
    CreaturesSectionBuilder().apply(block)

/** Builder for [CreaturesSection] instances. */
@BuilderDsl
internal class CreaturesSectionBuilder : TibiaKtBuilder<CreaturesSection> {
    var boostedCreature: CreatureEntry? = null
    val creatures: MutableList<CreatureEntry> = mutableListOf()

    fun boostedCreature(boostedCreature: CreatureEntry): CreaturesSectionBuilder =
        apply { this.boostedCreature = boostedCreature }

    fun boostedCreature(block: CreatureEntryBuilder.() -> Unit): CreaturesSectionBuilder =
        apply { this.boostedCreature = CreatureEntryBuilder().apply(block).build() }

    fun addCreature(creature: CreatureEntry): CreaturesSectionBuilder = apply { creatures.add(creature) }

    @BuilderDsl
    fun addCreature(block: CreatureEntryBuilder.() -> Unit): CreaturesSectionBuilder =
        apply { creatures.add(CreatureEntryBuilder().apply(block).build()) }

    override fun build(): CreaturesSection = CreaturesSection(
        boostedCreature = requireField(boostedCreature, "boostedCreature"),
        creatures = creatures,
    )

    @BuilderDsl
    class CreatureEntryBuilder : TibiaKtBuilder<CreatureEntry> {
        lateinit var name: String
        lateinit var identifier: String

        override fun build(): CreatureEntry = CreatureEntry(
            name = requireField(::name.isInitialized, "name") { name },
            identifier = requireField(::identifier.isInitialized, "identifier") { identifier },
        )
    }
}
