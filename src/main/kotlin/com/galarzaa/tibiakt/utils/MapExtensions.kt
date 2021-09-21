package com.galarzaa.tibiakt.utils

fun <V> Map<String, V>.getContaining(key: String, default: V? = null): V? {
    this.forEach { (k, v) ->
        if (k.contains(key)) {
            return v
        }
    }
    return default
}