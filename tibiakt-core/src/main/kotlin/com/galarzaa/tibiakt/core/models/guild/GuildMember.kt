/*
 * Copyright © 2024 Allan Galarza
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

package com.galarzaa.tibiakt.core.models.guild

import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.character.BaseCharacter
import com.galarzaa.tibiakt.core.models.character.CharacterLevel
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate

/**
 * A character that is part of a [Guild].
 *
 * @property rank The name of the rank the character holds.
 * @property title The title of the character in the guild.
 * @property level The current level of the character.
 * @property vocation The vocation of the character.
 * @property joinedOn The date when the character joined the guild.
 * @property isOnline Whether the character is currently online or not.
 */
@Serializable
public data class GuildMember(
    override val name: String,
    val rank: String,
    val title: String?,
    override val level: Int,
    val vocation: Vocation,
    val joinedOn: LocalDate,
    val isOnline: Boolean,
) : BaseCharacter, CharacterLevel
