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

package com.galarzaa.tibiakt.core.section.forum.section.builder

import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.builder.requireField
import com.galarzaa.tibiakt.core.section.forum.section.model.BoardEntry
import com.galarzaa.tibiakt.core.section.forum.section.model.ForumSection
import com.galarzaa.tibiakt.core.section.forum.shared.model.LastPost

internal inline fun forumsSectionBuilder(block: ForumsSectionBuilder.() -> Unit): ForumsSectionBuilder =
    ForumsSectionBuilder().apply(block)

internal inline fun forumsSection(block: ForumsSectionBuilder.() -> Unit): ForumSection =
    forumsSectionBuilder(block).build()

/** Builder for [ForumSection] instances. */
internal class ForumsSectionBuilder : TibiaKtBuilder<ForumSection> {
    var sectionId: Int = 0
    val entries: MutableList<BoardEntry> = mutableListOf()

    fun addEntry(block: BoardEntryBuilder.() -> Unit) {
        entries.add(BoardEntryBuilder().apply(block).build())
    }

    override fun build(): ForumSection = ForumSection(
        sectionId = sectionId,
        entries = entries
    )

    class BoardEntryBuilder : TibiaKtBuilder<BoardEntry> {
        var name: String? = null
        var boardId: Int = 0
        var description: String? = null
        var posts: Int = 0
        var threads: Int = 0
        var lastPost: LastPost? = null

        override fun build(): BoardEntry = BoardEntry(
            name = requireField(name, "name"),
            boardId = boardId,
            description = requireField(description, "description"),
            postsCount = posts,
            threadsCount = threads,
            lastPost = lastPost
        )
    }
}
