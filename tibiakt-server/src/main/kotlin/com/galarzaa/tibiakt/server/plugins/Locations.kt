@file:OptIn(KtorExperimentalLocationsAPI::class)

package com.galarzaa.tibiakt.server.plugins

import io.ktor.application.*
import io.ktor.locations.*
import java.time.LocalDate

internal fun Application.configureLocations() {
    install(Locations)
}

@Location("/characters/{name}")
data class GetCharacter(val name: String)

@Location("/worlds/{name}")
data class GetWorld(val name: String) {
    @Location("/guilds")
    data class Guilds(val parent: GetWorld)
}

@Location("guilds/{name}")
data class GetGuild(val name: String)

@Location("/news")
data class GetNewsArchive(val start: LocalDate?, val end: LocalDate?, val days: Int?)

@Location("/news/{newsId}")
data class GetNews(val newsId: Int) {
    @Location("/html")
    data class Html(val parent: GetNews)
}

@Location("killStatistics/{world}")
data class GetKillStatistics(val world: String)

@Location("eventsSchedule/{year}/{month}")
data class GetEventsSchedule(val year: Int, val month: Int)