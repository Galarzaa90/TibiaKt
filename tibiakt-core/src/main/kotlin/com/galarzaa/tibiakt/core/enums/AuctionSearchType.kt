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

package com.galarzaa.tibiakt.core.enums

/**
 * The types of search for the input field in the character bazaar.
 */
public enum class AuctionSearchType(override val value: Int) : IntEnum {
    /**
     * Searches everything that includes the words on the search string.
     */
    ITEM_DEFAULT(0),

    /**
     * Searches everything that includes the search string.
     */
    ITEM_WILDCARD(1),

    /**
     * Searches a character’s name.
     */
    CHARACTER_NAME(2);

    public companion object {
        public const val queryParam: String = "searchtype"
    }
}
