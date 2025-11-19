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



package com.galarzaa.tibiakt.core.section.community.house.model

import com.galarzaa.tibiakt.core.domain.house.BaseHouse
import kotlinx.serialization.Serializable
import kotlin.time.Duration

/**
 * A listed house in the Houses Section of tibia.com.
 *
 * @property name The name of the house.
 * @property size The size of the house in SQM.
 * @property rent The monthly rent paid for this house.
 * @property town The town where this house is located or closest too.
 * @property type The type of the house.
 * @property status The current status of the house.
 * @property highestBid The current highest bid of this house, if it is being auctioned.
 * @property timeLeft The remaining time for the auction to end. Note that the resolution of this is either days or hours.
 */
@Serializable
public data class HouseEntry(
    override val houseId: Int,
    val name: String,
    val size: Int,
    val rent: Int,
    val town: String,
    override val world: String,
    val type: HouseType,
    val status: HouseStatus,
    val highestBid: Int?,
    val timeLeft: Duration?,
) : BaseHouse
