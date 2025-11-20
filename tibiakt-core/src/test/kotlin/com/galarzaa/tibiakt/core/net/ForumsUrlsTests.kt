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

import com.galarzaa.tibiakt.core.section.forum.shared.model.AvailableForumSection
import com.galarzaa.tibiakt.core.section.forum.urls.cmPostArchiveUrl
import com.galarzaa.tibiakt.core.section.forum.urls.forumAnnouncementUrl
import com.galarzaa.tibiakt.core.section.forum.urls.forumBoardUrl
import com.galarzaa.tibiakt.core.section.forum.urls.forumPostUrl
import com.galarzaa.tibiakt.core.section.forum.urls.forumSectionUrl
import com.galarzaa.tibiakt.core.section.forum.urls.forumThreadUrl
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import kotlinx.datetime.LocalDate

class ForumsUrlsTests : FunSpec({
    context("forumSectionUrl") {
        test("by id") {
            val url = forumSectionUrl(42)

            url shouldContain "/forum"
            url shouldContain "action=main"
            url shouldContain "sectionid=42"
        }

        test("by enum") {
            // Pretend enum has a subtopic like "worldboards" or whatever your value is.
            val section = AvailableForumSection.WORLD_BOARDS
            val url = forumSectionUrl(section)

            url shouldContain "/forum"
            url shouldContain "subtopic=${section.subtopic}"
        }

        test("by name") {
            val url = forumSectionUrl("worldboards")

            url shouldContain "/forum"
            url shouldContain "subtopic=worldboards"
        }
    }
    context("forumBoardUrl") {
        test("defaults") {
            val url = forumBoardUrl(boardId = 100)

            url shouldContain "/forum"
            url shouldContain "action=board"
            url shouldContain "boardid=100"
            url shouldContain "pagenumber=1"
            // threadAge null should not leak into the query
            url shouldNotContain "threadage="
        }

        test("with page and threadAge") {
            val url = forumBoardUrl(boardId = 100, page = 7, threadAge = 30)

            url shouldContain "pagenumber=7"
            url shouldContain "threadage=30"
        }
    }

    test("forumAnnouncementUrl") {
        val url = forumAnnouncementUrl(555)

        url shouldContain "/forum"
        url shouldContain "action=announcement"
        url shouldContain "announcementid=555"
    }
    context("forumThreadUrl") {
        test("defaults") {
            val url = forumThreadUrl(threadId = 9876)

            url shouldContain "/forum"
            url shouldContain "action=thread"
            url shouldContain "threadid=9876"
            url shouldContain "pagenumber=1"
        }

        test("with page") {
            val url = forumThreadUrl(threadId = 9876, page = 3)

            url shouldContain "pagenumber=3"
        }
    }

    test("forumPostUrl with anchor") {
        val url = forumPostUrl(321)

        url shouldContain "/forum"
        url shouldContain "action=thread"
        url shouldContain "postid=321"
        url shouldContain "#post321"
    }
    context("cmPostArchiveUrl") {


        test("with date range and page") {
            val start = LocalDate(2024, 12, 25)
            val end = LocalDate(2025, 1, 5)
            val url = cmPostArchiveUrl(start, end, page = 4)

            url shouldContain "/forum"
            url shouldContain "subtopic=forum"
            url shouldContain "action=cm_post_archive"
            url shouldContain "startyear=2024"
            url shouldContain "startmonth=12"
            url shouldContain "startday=25"
            url shouldContain "endyear=2025"
            url shouldContain "endmonth=1"
            url shouldContain "endday=5"
            url shouldContain "currentpage=4"
        }

        test("defaults to page 1") {
            val start = LocalDate(2024, 1, 1)
            val end = LocalDate(2024, 1, 31)
            val url = cmPostArchiveUrl(start, end)

            url shouldContain "currentpage=1"
        }
    }
})
