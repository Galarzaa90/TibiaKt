package com.galarzaa.tibiakt.models

import io.ktor.client.statement.*

internal data class TimedResponse(val original: HttpResponse, val fetchingTime: Float)