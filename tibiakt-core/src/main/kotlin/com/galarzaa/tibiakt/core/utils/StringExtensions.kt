package com.galarzaa.tibiakt.core.utils

/**
 * Split a string enumerating elements, using a different separator for the last item.
 */
fun String?.splitList(separator: String = ",", lastSeparator: String = " and "): List<String> {
    val items = this?.split(separator)?.toMutableList() ?: return emptyList()
    val lastSplit: List<String> = items.last().split(lastSeparator)
    if (lastSplit.size > 1) {
        items[items.lastIndex] = lastSplit.subList(0, lastSplit.lastIndex).joinToString(lastSeparator)
        items.add(lastSplit.last())
    }
    return items.map { it.trim() }
}

/**
 * Remove an arbitrary string from a string, as many times as it is found.
 */
fun String.remove(value: String, ignoreCase: Boolean = false): String {
    return replace(value, "", ignoreCase)
}

/**
 * Clean the string of non-breaking spaces and trims whitespace.
 */
fun String.clean(): String {
    return replace("\u00A0", " ")
        .replace("&#xa0;", " ")
        .trim()
}


fun String.parseInteger(): Int = remove(",").trim().toInt()
fun String.findInteger(): Int = filter { it.isDigit() }.toInt()

fun String.parseLong(): Long = remove(",").trim().toLong()

fun String?.nullIfBlank(): String? = if (isNullOrBlank()) null else this

fun String.parseThousandPrefix(): Int {
    val kCount = filter { it == 'k' || it == 'K' }.count()
    return remove("k", true).parseInteger() * (kCount * 1000)
}
