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

package com.galarzaa.tibiakt.core.models.character

import kotlinx.serialization.Serializable

/**
 * Additional character listed in a [Character]'s information.
 *
 * @property world The world of the character.
 * @property isMain Whether this is the main character of the account or not.
 * @property isOnline Whether this character is currently online or not.
 * @property isDeleted Whether this character is scheduled for deletion or not.
 * @property recentlyTraded Whether this character was recently traded.
 * @property position Any special position the character holds.
 */
@Serializable
public data class OtherCharacter(
    override val name: String,
    val world: String,
    val isMain: Boolean,
    val isOnline: Boolean,
    val isDeleted: Boolean,
    val recentlyTraded: Boolean,
    val position: String?,
) : BaseCharacter
