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

package com.galarzaa.tibiakt.core.section.community.character.model

import com.galarzaa.tibiakt.core.domain.house.BaseHouse
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

/**
 * A house owned by a [Character].
 *
 * @property name The name of the house.
 * @property town The town where the city is or is closest to.
 * @property paidUntil The date when the last paid rent is due.
 */
@Serializable
public data class CharacterHouse(
    val name: String,
    override val houseId: Int,
    val town: String,
    val paidUntil: LocalDate,
    override val world: String,
) : BaseHouse
