package com.galarzaa.tibiakt.core

import java.net.URLEncoder

fun getTibiaUrl(section: String, vararg params: Pair<String, String>, test: Boolean = false): String {
    val baseUrl = if (test) "www.test.tibia.com" else "www.tibia.com"
    return "https://$baseUrl/$section/?${
        params.joinToString("&") { (name, value) ->
            "$name=${URLEncoder.encode(value, Charsets.ISO_8859_1)}"
        }
    }"
}

fun getCharacterUrl(name: String) = getTibiaUrl("community", Pair("subtopic", "characters"), Pair("name", name))
fun getWorldGuildsUrl(world: String) = getTibiaUrl("community", Pair("subtopic", "guilds"), Pair("world", world))
fun getGuildUrl(name: String) =
    getTibiaUrl("community", Pair("subtopic", "guilds"), Pair("GuildName", name), Pair("page", "view"))