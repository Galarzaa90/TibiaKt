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

package com.galarzaa.tibiakt.core.section.community.world.model

import com.galarzaa.tibiakt.core.domain.character.BaseCharacter
import com.galarzaa.tibiakt.core.domain.character.CharacterLevel
import com.galarzaa.tibiakt.core.domain.character.Vocation
import kotlinx.serialization.Serializable

/**
 * An online character.
 *
 * @property level The level of the character
 * @property vocation The vocation of the character
 */
@Serializable
public data class OnlineCharacter(
    override val name: String,
    override val level: Int,
    val vocation: Vocation,
) : BaseCharacter, CharacterLevel
