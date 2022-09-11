package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.models.forums.LastPost
import com.galarzaa.tibiakt.core.utils.BuilderDsl
import kotlinx.datetime.Instant

@BuilderDsl
public inline fun lastPostBuilder(block: LastPostBuilder.() -> Unit): LastPostBuilder = LastPostBuilder().apply(block)

@BuilderDsl
public inline fun lastPost(block: LastPostBuilder.() -> Unit): LastPost = lastPostBuilder(block).build()

@BuilderDsl
public class LastPostBuilder : TibiaKtBuilder<LastPost>() {
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
