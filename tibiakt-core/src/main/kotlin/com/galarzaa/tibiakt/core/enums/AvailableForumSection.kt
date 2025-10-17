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
 * The available forum sections.
 *
 * @property subtopic The query parameter value for "subtopic" used to access this board.
 */
public enum class AvailableForumSection(public val subtopic: String) {
    /** The World boards section, containing a dedicated board for every world. */
    WORLD_BOARDS("worldboards"),
    /** The trade boards section, containing a dedicated trading board for every world. */
    TRADE_BOARDS("tradeboards"),
    /** Community boards. */
    COMMUNITY_BOARDS("communityboards"),
    /** Support boards. */
    SUPPORT_BOARDS("supportboards"),
    /** Guild boards. */
    GUILD_BOARDS("guildboards"),
}
