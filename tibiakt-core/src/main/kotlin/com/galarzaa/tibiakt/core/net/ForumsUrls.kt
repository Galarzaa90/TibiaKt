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

package com.galarzaa.tibiakt.core.net

import com.galarzaa.tibiakt.core.enums.AvailableForumSection
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number

/**
 * Get the URL to a specific forum section.
 */
public fun forumSectionUrl(sectionId: Int): String =
    tibiaUrl("forum", "action" to "main", "sectionid" to sectionId)


/**
 * Get the URL to a specific forum section.
 */
public fun forumSectionUrl(section: AvailableForumSection): String =
    tibiaUrl("forum", section.subtopic)

/**
 * Get the URL to a specific forum section by its name.
 */
public fun forumSectionUrl(sectionName: String): String = tibiaUrl("forum", sectionName)

/** Get the URL to a forum board. */
public fun forumBoardUrl(boardId: Int, page: Int = 1, threadAge: Int? = null): String = tibiaUrl(
    "forum", "action" to "board", "boardid" to boardId, "pagenumber" to page, "threadage" to threadAge
)

/** Get the URL to a forum announcement. */
public fun forumAnnouncementUrl(announcementId: Int): String =
    tibiaUrl("forum", "action" to "announcement", "announcementid" to announcementId)

/**
 * Get the URL to a specific thread in the forums.
 */
public fun forumThreadUrl(threadId: Int, page: Int = 1): String =
    tibiaUrl("forum", "action" to "thread", "threadid" to threadId, "pagenumber" to page)

/**
 * Get the URL of a forum post with a specific [postId] in Tibia.com.
 */
public fun forumPostUrl(postId: Int): String =
    tibiaUrl("forum", "action" to "thread", "postid" to postId, anchor = "post$postId")


/**
 * Get the URL to the CM Post Archive in Tibia.com.
 */
public fun cmPostArchiveUrl(startDate: LocalDate, endDate: LocalDate, page: Int = 1): String {
    return tibiaUrl(
        "forum",
        "forum",
        "action" to "cm_post_archive",
        "startyear" to startDate.year,
        "startmonth" to startDate.month.number,
        "startday" to startDate.day,
        "endyear" to endDate.year,
        "endmonth" to endDate.month.number,
        "endday" to endDate.day,
        "currentpage" to page
    )
}
