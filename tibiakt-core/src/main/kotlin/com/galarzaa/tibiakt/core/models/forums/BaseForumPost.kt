package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.utils.getForumPostUrl

interface BaseForumPost {
    val postId: Int

    val url get() = getForumPostUrl(postId)
}