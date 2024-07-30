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
    BID(100),
    END_DATE(101),
    LEVEL(102),
    START_DATE(103),
    AXE_FIGHTING(10),
    CLUB_FIGHTING(9),
    DISTANCE_FIGHTING(7),
    FISHING(13),
    FIST_FIGHTING(11),
    MAGIC_LEVEL(1),
    SHIELDING(6),
    SWORD_FIGHTING(8);

    public companion object {
        /**
         * The query parameter name used to set this value.
         */
        public const val QUERY_PARAM: String = "order_column"
    }
}
