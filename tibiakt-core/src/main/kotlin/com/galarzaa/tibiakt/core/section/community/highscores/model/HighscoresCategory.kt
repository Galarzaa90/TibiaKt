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

package com.galarzaa.tibiakt.core.section.community.highscores.model

import com.galarzaa.tibiakt.core.enums.IntEnum

/** Available highscores categories. */
public enum class HighscoresCategory(override val value: Int) : IntEnum {
    ACHIEVEMENTS(1),
    AXE_FIGHTING(2),
    BOSS_POINTS(15),
    BOUNTY_POINTS_EARNED(16),
    CHARM_POINTS(3),
    CLUB_FIGHTING(4),
    DISTANCE_FIGHTING(5),
    DROME_SCORE(14),
    EXPERIENCE_POINTS(6),
    FISHING(7),
    FIST_FIGHTING(8),
    GOSHNARS_TAINT(9),
    LOYALTY_POINTS(10),
    MAGIC_LEVEL(11),
    SHIELDING(12),
    SWORD_FIGHTING(13),
    WEEKLY_TASKS_COMPLETED(17);
}
