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

import com.galarzaa.tibiakt.core.models.forums.CMPost
import com.galarzaa.tibiakt.core.models.forums.CMPostArchive
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.LocalDate


@BuilderDsl
public inline fun cmPostArchive(block: CMPostArchiveBuilder.() -> Unit): CMPostArchive =
    CMPostArchiveBuilder().apply(block).build()

@BuilderDsl
public inline fun cmPostArchiveBuilder(block: CMPostArchiveBuilder.() -> Unit): CMPostArchiveBuilder =
    CMPostArchiveBuilder().apply(block)

@BuilderDsl
public class CMPostArchiveBuilder : TibiaKtBuilder<CMPostArchive> {
    public var startDate: LocalDate? = null
    public var endDate: LocalDate? = null
    public var currentPage: Int = 1
    public var totalPages: Int = 1
    public var resultsCount: Int = 0
    public val entries: MutableList<CMPost> = mutableListOf()

    public fun addEntry(post: CMPost): CMPostArchiveBuilder = apply { entries.add(post) }

    override fun build(): CMPostArchive = CMPostArchive(
        startDate = startDate ?: error("startDate is required"),
        endDate = endDate ?: error("endDate is required"),
        currentPage = currentPage,
        totalPages = totalPages,
        resultsCount = resultsCount,
        entries = entries,
    )
}
