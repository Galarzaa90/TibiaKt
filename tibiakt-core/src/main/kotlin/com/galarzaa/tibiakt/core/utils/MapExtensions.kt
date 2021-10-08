package com.galarzaa.tibiakt.core.utils

/**
 * Get a mapping's key containing the string.
 */
fun <V> Map<String, V>.getContaining(key: String, default: V? = null): V? {
    for ((k, v) in this)
        if (k.contains(key))
            return v
    return default
}