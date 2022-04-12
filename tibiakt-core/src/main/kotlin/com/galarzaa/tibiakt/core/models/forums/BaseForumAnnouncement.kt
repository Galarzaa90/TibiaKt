package com.galarzaa.tibiakt.core.models.forums

import com.galarzaa.tibiakt.core.utils.getForumAnnouncementUrl

interface BaseForumAnnouncement {
    val title: String
    val announcementId: Int

    val url get() = getForumAnnouncementUrl(announcementId)
}