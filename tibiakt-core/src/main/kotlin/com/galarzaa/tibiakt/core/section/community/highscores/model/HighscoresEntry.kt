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

package com.galarzaa.tibiakt.core.section.community.highscores.model

import com.galarzaa.tibiakt.core.domain.character.LevelAware
import com.galarzaa.tibiakt.core.domain.character.TibiaCharacter
import com.galarzaa.tibiakt.core.domain.character.Vocation
import kotlinx.serialization.Serializable

/**
 * An entry in the [Highscores].
 *
 * @property rank The rank of the character. Multiple entries might have the same rank if they are tied.
 * @property level The level of the character.
 * @property world The world of the character.
 * @property vocation The vocation of the character.
 * @property value The value of the entry (e.g. distance level, magic level, experience)
 * @property additionalValue Additional information displayed in some categories, such as Loyalty Title.
 */
@Serializable
public data class HighscoresEntry(
    val rank: Int,
    override val name: String,
    override val level: Int,
    val world: String,
    val vocation: Vocation,
    val value: Long,
    val additionalValue: String?,
) : TibiaCharacter, LevelAware
