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


package com.galarzaa.tibiakt.core.section.news.archive.model

import com.galarzaa.tibiakt.core.section.news.shared.model.BaseNews
import com.galarzaa.tibiakt.core.section.news.shared.model.NewsCategory
import com.galarzaa.tibiakt.core.section.news.shared.model.NewsType
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

/**
 * A news entry listed in the [NewsArchive].
 *
 * @property title The title of the entry
 * @property publishedOn The date when the entry was published.
 * @property type The type of the entry.
 */
@Serializable
public data class NewsEntry(
    override val id: Int,
    val title: String,
    override val category: NewsCategory,
    val publishedOn: LocalDate,
    val type: NewsType,
) : BaseNews
