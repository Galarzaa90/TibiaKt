package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.forums.LastPost
import java.time.Instant

inline fun lastPostBuilder(block: LastPostBuilder.() -> Unit) = LastPostBuilder().apply(block)
inline fun lastPost(block: LastPostBuilder.() -> Unit) = lastPostBuilder(block).build()

@TibiaKtDsl
class LastPostBuilder {
    var author: String? = null
    var postId: Int? = null
    var date: Instant? = null
    var deleted: Boolean = false
    var traded: Boolean = false

    fun build() = LastPost(
        author = author ?: throw IllegalArgumentException("author is required"),
        postId = postId ?: throw IllegalArgumentException("postId is required"),
        date = date ?: throw IllegalArgumentException("date is required"),
        deleted = deleted,
        traded = traded
    )
}