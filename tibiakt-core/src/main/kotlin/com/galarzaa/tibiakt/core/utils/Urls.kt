package com.galarzaa.tibiakt.core.utils

import com.galarzaa.tibiakt.core.enums.HighscoresBattlEyeType
import com.galarzaa.tibiakt.core.enums.HighscoresCategory
import com.galarzaa.tibiakt.core.enums.HighscoresProfession
import com.galarzaa.tibiakt.core.enums.HighscoresPvpType
import java.net.URLEncoder

private typealias P<A, B> = Pair<A, B>

fun getTibiaUrl(section: String, params: Map<String, Any>, test: Boolean = false): String {
    return getTibiaUrl(section, params = params.toList().toTypedArray(), test = test)
}

fun getTibiaUrl(section: String, vararg params: Pair<String, Any?>, test: Boolean = false): String {
    val baseUrl = if (test) "www.test.tibia.com" else "www.tibia.com"
    return "https://$baseUrl/$section/?${
        params.filter { it.second != null }.joinToString("&") { (name, value) ->
            "$name=${URLEncoder.encode(value.toString(), Charsets.ISO_8859_1)}"
        }
    }"
}

fun getCharacterUrl(name: String) = getTibiaUrl("community", P("subtopic", "characters"), P("name", name))
fun getWorldGuildsUrl(world: String) = getTibiaUrl("community", P("subtopic", "guilds"), P("world", world))
fun getGuildUrl(name: String) =
    getTibiaUrl("community", P("subtopic", "guilds"), P("GuildName", name), P("page", "view"))

fun getHouseUrl(world: String, houseId: Int) =
    getTibiaUrl("community", P("subtopic", "houses"), P("world", world), P("houseid", houseId))

fun getWorldOverviewUrl() = getTibiaUrl("community", P("subtopic", "worlds"))
fun getWorldUrl(name: String) = getTibiaUrl("community", P("subtopic", "worlds"), P("world", name))
fun getWorldGuilds(world: String) = getTibiaUrl("community", P("subtopic", "guilds"), P("world", world))
fun getNewsArchiveUrl() = getTibiaUrl("news", P("subtopic", "newsarchive"))
fun getNewsUrl(newsId: Int) = getTibiaUrl("news", P("subtopic", "newsarchive"), P("id", newsId))
fun getThreadUrl(threadId: Int) = getTibiaUrl("forum", P("action", "thread"), P("threadid", threadId))
fun getHighscoresUrl(
    world: String?,
    category: HighscoresCategory = HighscoresCategory.EXPERIENCE_POINTS,
    vocations: HighscoresProfession = HighscoresProfession.ALL,
    page: Int = 1,
    battleEye: HighscoresBattlEyeType = HighscoresBattlEyeType.ANY_WORLD,
    worldTypes: Set<HighscoresPvpType>? = null
) = getTibiaUrl(
    "community",
    P("subtopic", "highscores"),
    P("world", world),
    P("profession", vocations.value),
    P("curentpage", page),
    P("category", category.value),
    P("beprotection", battleEye.value),
    *worldTypes.orEmpty().map { P("worldtypes[]", it.value) }.toTypedArray()
)

fun getKillStatisticsUrl(world: String) = getTibiaUrl("community", P("subtopic", "killstatistics"), P("world", world))