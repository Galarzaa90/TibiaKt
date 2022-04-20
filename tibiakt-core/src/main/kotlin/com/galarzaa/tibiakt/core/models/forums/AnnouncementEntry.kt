package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.utils.getCharacterUrl
import kotlinx.serialization.Serializable

/**
 * An announcement listed on a [ForumBoard].
 *
 * The detailed view is represented by [ForumAnnouncement].
 *
 * @property author The name of the author of the announcement.
 */
@Serializable
data class AnnouncementEntry(
    override val title: String,
    override val announcementId: Int,
    val author: String,
) : BaseForumAnnouncement {

    /** The URL to the author's character page. */
    val authorUrl get() = getCharacterUrl(author)
}

