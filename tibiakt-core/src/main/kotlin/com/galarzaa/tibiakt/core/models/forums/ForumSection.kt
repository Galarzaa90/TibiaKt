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

package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.utils.getForumSectionUrl
import kotlinx.serialization.Serializable

/**
 * A forum section on Tibia.com.
 *
 * @property sectionId The internal ID of the section.
 * @property entries The boards in the forum section.
 */
@Serializable
public data class ForumSection(
    val sectionId: Int,
    val entries: List<BoardEntry>,
) {
    /** The URL to this section. */
    val url: String get() = getForumSectionUrl(sectionId)
}
