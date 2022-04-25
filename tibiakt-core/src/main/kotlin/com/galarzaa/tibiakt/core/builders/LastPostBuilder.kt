package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.forums.LastPost
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import java.time.Instant

@BuilderDsl
inline fun lastPostBuilder(block: LastPostBuilder.() -> Unit) = LastPostBuilder().apply(block)

@BuilderDsl
inline fun lastPost(block: LastPostBuilder.() -> Unit) = lastPostBuilder(block).build()

@BuilderDsl
class LastPostBuilder : TibiaKtBuilder<LastPost>() {
    var author: String? = null
    var postId: Int? = null
    var date: Instant? = null
    var deleted: Boolean = false
    var traded: Boolean = false

    override fun build() = LastPost(
        author = author ?: throw IllegalArgumentException("author is required"),
        postId = postId ?: throw IllegalArgumentException("postId is required"),
        date = date ?: throw IllegalArgumentException("date is required"),
        deleted = deleted,
        traded = traded
    )
}