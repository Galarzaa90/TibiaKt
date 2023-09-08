/*
 * Copyright © 2023 Allan Galarza
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

package com.galarzaa.tibiakt.core.models.character

import kotlinx.serialization.Serializable

/**
 * The achievements chosen to be displayed for a [Character].
 *
 * @property name The name of the achievement.
 * @property grade The grade of the achievement, also known as the number of stars.
 * @property isSecret Whether this is a secret achievement or not.
 */
@Serializable
public data class DisplayedAchievement(val name: String, val grade: Int, val isSecret: Boolean)
