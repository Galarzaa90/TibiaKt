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

package com.galarzaa.tibiakt.core.section.forum.cmpost.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.builder.requireField
import com.galarzaa.tibiakt.core.section.forum.cmpost.model.CMPost
import com.galarzaa.tibiakt.core.section.forum.cmpost.model.CMPostArchive
import kotlinx.datetime.LocalDate

@BuilderDsl
internal inline fun cmPostArchive(block: CMPostArchiveBuilder.() -> Unit): CMPostArchive =
    CMPostArchiveBuilder().apply(block).build()

@BuilderDsl
internal inline fun cmPostArchiveBuilder(block: CMPostArchiveBuilder.() -> Unit): CMPostArchiveBuilder =
    CMPostArchiveBuilder().apply(block)

/** Builder for [CMPostArchive] instances. */
@BuilderDsl
internal class CMPostArchiveBuilder : TibiaKtBuilder<CMPostArchive> {
    var startOn: LocalDate? = null
    var endOn: LocalDate? = null
    var currentPage: Int = 1
    var totalPages: Int = 1
    var resultsCount: Int = 0
    val entries: MutableList<CMPost> = mutableListOf()

    fun addEntry(post: CMPost): CMPostArchiveBuilder = apply { entries.add(post) }

    override fun build(): CMPostArchive = CMPostArchive(
        startOn = requireField(startOn, "startOn"),
        endOn = requireField(endOn, "endOn"),
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        entries = entries,
    )
}
