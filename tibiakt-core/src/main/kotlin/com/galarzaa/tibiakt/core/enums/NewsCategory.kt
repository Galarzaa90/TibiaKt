package com.galarzaa.tibiakt.core.enums

enum class NewsCategory(val value: String) {
    CIPSOFT("cipsoft"),
    COMMUNITY("community"),
    DEVELOPMENT("development"),
    SUPPORT("support"),
    TECHNICAL_ISSUES("technical");

    val filterName
        get() = "filter_$value"

    companion object {
        private val iconRegex = Regex("""newsicon_([^_]+)_(?:small|big)""")
        fun fromIcon(iconUrl: String): NewsCategory? {
            iconRegex.find(iconUrl)?.apply {
                return fromValue(this.groups[1]!!.value.lowercase())
            }
            return null
        }

        fun fromValue(value: String) = values().find { it.value == value }
    }
}