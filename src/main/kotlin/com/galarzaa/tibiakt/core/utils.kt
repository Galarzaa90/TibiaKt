package com.galarzaa.tibiakt.core

import java.net.URLEncoder

fun getTibiaUrl(section: String, vararg params: Pair<String, String>, test: Boolean = false): String {
    val baseUrl = if (test) "www.test.tibia.com" else "www.tibia.com"
    return "https://$baseUrl/$section/?${params.joinToString ( "&" ){  (name, value) ->
        "$name=${URLEncoder.encode(value, Charsets.ISO_8859_1)}"}}"
}