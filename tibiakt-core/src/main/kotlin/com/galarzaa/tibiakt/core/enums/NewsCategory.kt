package com.galarzaa.tibiakt.core.enums

enum class NewsCategory(override val value: String) : StringEnum {
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
                return StringEnum.fromValue(this.groups[1]!!.value.lowercase())
            }
            return null
        }
    }
}