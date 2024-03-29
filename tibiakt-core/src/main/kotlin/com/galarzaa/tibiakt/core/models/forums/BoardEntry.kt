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

import kotlinx.serialization.Serializable

/**
 * A forum board listen within a [ForumSection].
 *
 * @property name The name of the board.
 * @property description The description of the board.
 * @property posts The number of posts in the board.
 * @property threads The number of threads in the board.
 * @property lastPost The last post in the board.
 */
@Serializable
public data class BoardEntry(
    val name: String,
    override val boardId: Int,
    val description: String,
    val posts: Int,
    val threads: Int,
    val lastPost: LastPost?,
) : BaseForumBoard
