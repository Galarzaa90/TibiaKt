package com.galarzaa.tibiakt.utils

import java.net.URLEncoder

private typealias P<A, B> = Pair<A, B>

fun getTibiaUrl(section: String, params: Map<String, Any>, test: Boolean = false): String {
    return getTibiaUrl(section, params = params.toList().toTypedArray(), test = test)
}

fun getTibiaUrl(section: String, vararg params: Pair<String, Any>, test: Boolean = false): String {
    val baseUrl = if (test) "www.test.tibia.com" else "www.tibia.com"
    return "https://$baseUrl/$section/?${
        params.joinToString("&") { (name, value) ->
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