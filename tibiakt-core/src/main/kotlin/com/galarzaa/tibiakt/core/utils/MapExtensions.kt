package com.galarzaa.tibiakt.core.utils

/**
 * Get a mapping's key containing the string.
 *
 * @param key The key to search for.
 * @param default The value to return if the key is not found.
 */
public fun <V> Map<String, V>.getContaining(key: String, default: V? = null): V? {
    for ((k, v) in this)
        if (k.contains(key))
            return v
    return default
}
