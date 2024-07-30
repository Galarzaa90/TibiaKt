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

package com.galarzaa.tibiakt.core.enums

/**
 * A world PvP type.
 */
public enum class PvpType(override val value: String) : StringEnum {
    OPEN_PVP("Open PvP") {
        override val weight: Int = 2
        override val highscoresFilterValue: Int = 0
        override val bazaarFilterValue: Int = 0
    },
    OPTIONAL_PVP("Optional PvP") {
        override val weight: Int = 1
        override val highscoresFilterValue: Int = 1
        override val bazaarFilterValue: Int = 1
    },
    RETRO_OPEN_PVP("Retro Open PvP") {
        override val weight: Int = 2
        override val highscoresFilterValue: Int = 3
        override val bazaarFilterValue: Int = 3
    },
    RETRO_HARDCORE_PVP("Retro Hardcore PvP") {
        override val weight: Int = 3
        override val highscoresFilterValue: Int = 4
        override val bazaarFilterValue: Int = 4
    },
    HARDCORE_PVP("Hardcore PvP") {
        override val weight: Int = 3
        override val highscoresFilterValue: Int = 2
        override val bazaarFilterValue: Int = 2
    };

    /**
     * The restriction weight of this type, used to check transferability between worlds.
     */
    public abstract val weight: Int

    /**
     * The value used internally to filter Highscores.
     */
    public abstract val highscoresFilterValue: Int

    /**
     * The value used internally to filter the CharacterBazaar.
     */
    public abstract val bazaarFilterValue: Int

    public companion object {
        /**
         * The name of the query parameter used to set this value in the highscores section.
         */
        public const val QUERY_PARAM_HIGHSCORES: String = "worldtypes"

        /**
         * The name of the query parameter used to set this value in the bazaar section.
         */
        public const val QUERY_PARAM_BAZAAR: String = "filter_worldpvptype"

        /**
         * Get the entry corresponding to the matching highscores query parameter value.
         */
        public fun fromHighscoresFilterValue(value: Int?): PvpType? =
            enumValues<PvpType>().firstOrNull { it.highscoresFilterValue == value }

        /**
         * Get the entry corresponding to the matching bazaar query parameter value.
         */
        public fun fromBazaarFilterValue(value: Int?): PvpType? =
            enumValues<PvpType>().firstOrNull { it.bazaarFilterValue == value }
    }
}
