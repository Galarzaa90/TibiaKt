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

package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.forums.LastPost
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import kotlinx.datetime.Instant

@BuilderDsl
public inline fun lastPostBuilder(block: LastPostBuilder.() -> Unit): LastPostBuilder = LastPostBuilder().apply(block)

@BuilderDsl
public inline fun lastPost(block: LastPostBuilder.() -> Unit): LastPost = lastPostBuilder(block).build()

/** Builder for [LastPost] instances. */
@BuilderDsl
public class LastPostBuilder : TibiaKtBuilder<LastPost> {
    public var author: String? = null
    public var postId: Int? = null
    public var date: Instant? = null
    public var deleted: Boolean = false
    public var traded: Boolean = false

    override fun build(): LastPost = LastPost(
        author = author ?: throw IllegalArgumentException("author is required"),
        postId = postId ?: throw IllegalArgumentException("postId is required"),
        date = date ?: throw IllegalArgumentException("date is required"),
        deleted = deleted,
        traded = traded
    )
}
