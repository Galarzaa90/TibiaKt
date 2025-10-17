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
 * The possible fields you can order auctions by.
 */
public enum class AuctionOrderBy(override val value: Int) : IntEnum {

    /** Order by the highest current bid amount. */
    BID(100),

    /** Order by the auction's end date. */
    END_DATE(101),

    /** Order by the character's level. */
    LEVEL(102),

    /** Order by the auction's start date. */
    START_DATE(103),

    /** Order by the character's axe fighting skill. */
    AXE_FIGHTING(10),

    /** Order by the character's club fighting skill. */
    CLUB_FIGHTING(9),

    /** Order by the character's distance fighting skill. */
    DISTANCE_FIGHTING(7),

    /** Order by the character's fishing skill. */
    FISHING(13),

    /** Order by the character's fist fighting skill. */
    FIST_FIGHTING(11),

    /** Order by the character's magic level. */
    MAGIC_LEVEL(1),

    /** Order by the character's shielding skill. */
    SHIELDING(6),

    /** Order by the character's sword fighting skill. */
    SWORD_FIGHTING(8);

    public companion object {
        /**
         * The query parameter name used to set this value.
         */
        public const val QUERY_PARAM: String = "order_column"
    }
}
