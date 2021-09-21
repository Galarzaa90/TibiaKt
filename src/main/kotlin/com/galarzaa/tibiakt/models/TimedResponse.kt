package com.galarzaa.tibiakt.models

import io.ktor.client.statement.*
import io.ktor.util.date.*

internal data class TimedResponse(val original: HttpResponse, val fetchingTime: Float)

internal fun <T> TimedResponse.toTibiaResponse(parsingTime: Float, data: T): TibiaResponse<T> {
    val isCached = original.headers["CF-Cache-Status"] == "HIT"
    val age = original.headers["Age"]?.toInt() ?: 0
    return TibiaResponse(
        timestamp = original.responseTime.toJvmDate().toInstant(),
        isCached = isCached,
        cacheAge = age,
        fetchingTime = fetchingTime,
        parsingTime = parsingTime,
        data = data
    )
}