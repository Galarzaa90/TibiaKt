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
 * The possible bid types an auction might have.
 */
public enum class BidType(override val value: String) : StringEnum {
    /**
     * The current highest bid, meaning the auction has received at least one bid.
     */
    CURRENT("Current Bid"),

    /**
     * The minimum bid set by the auction author, meaning the auction didn't receive any bids.
     */
    MINIMUM("Minimum Bid"),

    /** The bid that won the auction. */
    WINNING("Winning Bid"),
}
