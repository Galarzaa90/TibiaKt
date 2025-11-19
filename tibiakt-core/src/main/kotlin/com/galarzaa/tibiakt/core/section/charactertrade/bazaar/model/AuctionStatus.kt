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

import com.galarzaa.tibiakt.core.enums.StringEnum

/**
 * The possible values an auction might have.
 */
public enum class AuctionStatus(override val value: String) : StringEnum {
    /**
     * The auction is currently active.
     *
     * Note: This status doesn't exist in Tibia.com explicitly. It is given to all ongoing auctions.
     */
    IN_PROGRESS("in progress"),

    /** The auction ended with a winner, but payment hasn’t been received yet. */
    CURRENTLY_PROCESSED("currently processed"),

    /** The auction was finished and was paid, but the character hasn’t been transferred to the new owner yet. */
    PENDING_TRANSFER("will be transferred at the next server save"),

    /** The auction was cancelled as no payment was received in time. */
    CANCELLED("cancelled"),

    /** The auction either finished with no bids or the character was transferred to the new owner already. */
    FINISHED("finished"),
}
