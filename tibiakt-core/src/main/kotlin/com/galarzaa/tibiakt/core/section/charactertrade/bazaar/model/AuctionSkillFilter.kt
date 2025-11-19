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

package com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model

import com.galarzaa.tibiakt.core.enums.IntEnum

/**
 * The possible skills to filter auctions by.
 */
public enum class AuctionSkillFilter(override val value: Int) : IntEnum {

    /** Filter by the character's axe fighting skill. */
    AXE_FIGHTING(10),

    /** Filter by the character's club fighting skill. */
    CLUB_FIGHTING(9),

    /** Filter by the character's distance fighting skill. */
    DISTANCE_FIGHTING(7),

    /** Filter by the character's fishing skill. */
    FISHING(13),

    /** Filter by the character's fist fighting skill. */
    FIST_FIGHTING(11),

    /** Filter by the character's magic level. */
    MAGIC_LEVEL(1),

    /** Filter by the character's shielding skill. */
    SHIELDING(6),

    /** Filter by the character's sword fighting skill. */
    SWORD_FIGHTING(8);

    public companion object {
        /**
         * The query parameter name used to set this value.
         */
        public const val QUERY_PARAM: String = "filter_skillid"
    }
}
