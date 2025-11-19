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



package com.galarzaa.tibiakt.core.section.forum.cmpost.model

import com.galarzaa.tibiakt.core.section.forum.shared.model.BaseForumPost
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * A post made by a Community Manager in the [CMPostArchive].
 *
 * @property postId The ID of the post.
 * @property postedAt The date and time when the post was created.
 * @property boardName The name of the board where the post is.
 * @property threadTitle The title of the thread where the post is.
 */
@Serializable
public data class CMPost(
    override val postId: Int,
    val postedAt: Instant,
    val boardName: String,
    val threadTitle: String,
) : BaseForumPost
