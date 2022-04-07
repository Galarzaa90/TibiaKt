package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.utils.getForumSectionUrl
import kotlinx.serialization.Serializable

/**
 * A forum section on Tibia.com
 *
 * @property sectionId The internal ID of the section.
 * @property entries The boards in the forum section.
 */
@Serializable
data class ForumSection(
    val sectionId: Int,
    val entries: List<BoardEntry>,
) {
    /** The URL to this section. */
    val url get() = getForumSectionUrl(sectionId)
}