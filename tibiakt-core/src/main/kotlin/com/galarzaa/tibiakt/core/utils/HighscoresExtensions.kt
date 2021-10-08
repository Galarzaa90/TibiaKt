package com.galarzaa.tibiakt.core.utils

import com.galarzaa.tibiakt.core.models.Highscores

fun Highscores.getPageUrl(page: Int) = getHighscoresUrl(world, category, vocation, page, battlEyeType, worldTypes)
val Highscores.url
    get() = getPageUrl(currentPage)
val Highscores.nextPageUrl: String?
    get() = if (currentPage == totalPages) null else getPageUrl(currentPage + 1)
val Highscores.previousPageUrl: String?
    get() = if (currentPage == 0) null else getPageUrl(currentPage - 1)