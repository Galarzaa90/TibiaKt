package com.galarzaa.tibiakt.utils

import com.galarzaa.tibiakt.models.BaseNews
import com.galarzaa.tibiakt.models.News

val BaseNews.url
    get() = getNewsUrl(id)

val News.threadUrl
    get() = if (threadId != null) getThreadUrl(threadId) else null