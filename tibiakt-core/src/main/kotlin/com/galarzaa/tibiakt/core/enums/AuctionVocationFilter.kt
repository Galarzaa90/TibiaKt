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
 * The possible vocations to filter auction results.
 */
public enum class AuctionVocationFilter(override val value: Int) : IntEnum {

    /** No vocation. */
    NONE(1),

    /** Any vocation. */
    ANY(0),

    /** Druids and Elder Druids. */
    DRUID(2),

    /** Knights and Elite Knights. */
    KNIGHT(3),

    /** Paladins and Royal Paladins. */
    PALADIN(4),

    /** Sorcerer and Master Sorcerers. */
    SORCERER(5),

    /** Monks and Exalted Monks. */
    MONK(6);

    public companion object {
        /**
         * The query parameter name used to set this value.
         */
        public const val QUERY_PARAM: String = "filter_profession"
    }
}
