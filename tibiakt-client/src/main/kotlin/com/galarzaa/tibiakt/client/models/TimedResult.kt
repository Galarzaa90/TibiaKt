package com.galarzaa.tibiakt.client.models

data class TimedResult<T>(
    val time: Float,
    val result: T,
)