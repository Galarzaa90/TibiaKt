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

package com.galarzaa.tibiakt.core.models.creatures

import kotlinx.serialization.Serializable

/**
 * The boostable bosses section of Tibia.com.
 *
 * @property boostedBoss The boosted boss of the day.
 * @property bosses The list of bostable bosses.
 */
@Serializable
public data class BosstableBosses(
    val boostedBoss: BossEntry,
    val bosses: List<BossEntry>,
)
