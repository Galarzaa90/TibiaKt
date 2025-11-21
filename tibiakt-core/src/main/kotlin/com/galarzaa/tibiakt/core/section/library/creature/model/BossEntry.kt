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

package com.galarzaa.tibiakt.core.section.library.creature.model

import kotlinx.serialization.Serializable

/**
 * A boss in the [BoostableBosses] section of Tibia.com.
 *
 * @property name The name of the boss.
 * @property identifier The internal name of the boss. Used for images.
 */
@Serializable
public data class BossEntry(
    override val name: String,
    override val identifier: String,
) : BaseCreature
