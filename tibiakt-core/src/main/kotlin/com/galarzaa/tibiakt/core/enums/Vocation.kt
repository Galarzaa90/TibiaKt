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
 * Enumeration of Tibia vocations.
 */
public enum class Vocation(override val value: String) : StringEnum {
    NONE("None"),
    DRUID("Druid"),
    SORCERER("Sorcerer"),
    PALADIN("Paladin"),
    KNIGHT("Knight"),
    MONK("Monk"),
    ELDER_DRUID("Elder Druid"),
    MASTER_SORCERER("Master Sorcerer"),
    ROYAL_PALADIN("Royal Paladin"),
    ELITE_KNIGHT("Elite Knight"),
    EXALTED_MONK("Exalted Monk");

    /**
     * Gets the base form of a vocation.
     */
    public val base: Vocation
        get() = when (this) {
            ELDER_DRUID -> DRUID
            MASTER_SORCERER -> SORCERER
            ROYAL_PALADIN -> PALADIN
            ELITE_KNIGHT -> KNIGHT
            EXALTED_MONK -> MONK
            else -> this
        }
}
