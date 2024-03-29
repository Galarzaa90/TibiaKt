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

@file:UseSerializers(LocalDateSerializer::class)

package com.galarzaa.tibiakt.core.models.news

import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.enums.NewsType
import com.galarzaa.tibiakt.core.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate

/**
 * The News Archive section in Tibia.com.
 *
 * @property startDate The start date of the articles displayed.
 * @property endDate The end date of the articles displayed.
 * @property types The types of articles to show.
 * @property categories The categories of articles to show.
 * @property entries The entries matching the filters.
 */
@Serializable
public data class NewsArchive(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val types: Set<NewsType>,
    val categories: Set<NewsCategory>,
    val entries: List<NewsEntry>,
)
