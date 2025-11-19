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

package com.galarzaa.tibiakt.core.domain.world

/**
 * The type of BattlEye protection a game world might have.
 *
 * @property weight The restriction weight of this type, used to check transferability between worlds.
 */
public enum class BattlEyeType(public val weight: Int) {
    /**
     * Protected by BattlEye since the beginning.
     */
    GREEN(2),

    /**
     * Protected by BattlEye at a later date.
     */
    YELLOW(1),

    /**
     * Not protected by BattlEye.
     */
    UNPROTECTED(0),
}
