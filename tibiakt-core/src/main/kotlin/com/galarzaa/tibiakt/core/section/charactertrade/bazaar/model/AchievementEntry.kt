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

package com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model

import kotlinx.serialization.Serializable

/**
 * An achievement in an [Auction].
 *
 * @property name The name of the achievement.
 * @property isSecret Whether the achievement is secret or not.
 */
@Serializable
public data class AchievementEntry(
    val name: String,
    val isSecret: Boolean,
)
