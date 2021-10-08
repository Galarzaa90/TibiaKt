package com.galarzaa.tibiakt.client.models

import io.ktor.client.statement.*
import io.ktor.util.date.*

internal data class TimedResponse(val original: HttpResponse, val fetchingTime: Float) {
    constructor(original: HttpResponse) : this(
        original,
        (
                original.responseTime.toJvmDate().toInstant().toEpochMilli() -
                        original.requestTime.toJvmDate().toInstant().toEpochMilli()) / 1000f
    )
}