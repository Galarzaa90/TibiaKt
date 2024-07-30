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
    WORLD_BOARDS("worldboards"),
    TRADE_BOARDS("tradeboards"),
    COMMUNITY_BOARDS("communityboards"),
    SUPPORT_BOARDS("supportboards"),
    GUILD_BOARDS("guildboards"),
}
