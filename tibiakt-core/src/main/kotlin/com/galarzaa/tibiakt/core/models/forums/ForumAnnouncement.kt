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


package com.galarzaa.tibiakt.core.models.forums

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * An announcement in the Tibia forums.
 *
 * @property board The name of the board the announcement is posted in.
 * @property boardId The internal ID of the board the announcement is in.
 * @property section The name of the section the announcement is posted in.
 * @property sectionId The internal ID of the section the announcement is in.
 * @property author The author that created the announcement.
 * @property content The content of the announcement.
 * @property startDate The start date of the announcement.
 * @property endDate The end date of the announcement.
 */
@Serializable
public data class ForumAnnouncement(
    override val announcementId: Int,
    override val title: String,
    val board: String,
    val boardId: Int,
    val section: String,
    val sectionId: Int,
    val author: BaseForumAuthor,
    val content: String,
    val startDate: Instant,
    val endDate: Instant,
) : BaseForumAnnouncement
