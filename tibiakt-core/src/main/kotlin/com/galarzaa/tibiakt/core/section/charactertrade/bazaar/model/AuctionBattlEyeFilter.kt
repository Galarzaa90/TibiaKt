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
 * The possible BattlEye protection type filters for auctions.
 */
public enum class AuctionBattlEyeFilter(override val value: Int) : IntEnum {
    /**
     * Worlds that have been protected from the beginning. These have a green icon.
     */
    INITIALLY_PROTECTED(1),

    /**
     * Worlds that had BattlEye implemented at a later date. These have a yellow icon.
     */
    PROTECTED(2),

    /**
     * Worlds that have no BattlEye protection at all.
     */
    NOT_PROTECTED(3);

    public companion object {
        /**
         * The query parameter name used to set this value.
         */
        public const val QUERY_PARAM: String = "filter_worldbattleyestate"
    }
}
