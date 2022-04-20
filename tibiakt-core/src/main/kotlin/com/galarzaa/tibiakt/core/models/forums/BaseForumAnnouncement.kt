package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.utils.getForumAnnouncementUrl

/** Base interface for forum announcements.
 *
 * @property title The title of the announcement.
 * @property announcementId The internal ID of the announcement, used for linking.
 */
interface BaseForumAnnouncement {
    val title: String
    val announcementId: Int

    val url get() = getForumAnnouncementUrl(announcementId)
}