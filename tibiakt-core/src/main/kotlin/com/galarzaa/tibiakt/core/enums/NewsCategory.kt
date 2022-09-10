package com.galarzaa.tibiakt.core.enums

import com.galarzaa.tibiakt.core.utils.getStaticFileUrl

public enum class NewsCategory(override val value: String) : StringEnum {
    CIPSOFT("cipsoft"),
    COMMUNITY("community"),
    DEVELOPMENT("development"),
    SUPPORT("support"),
    TECHNICAL_ISSUES("technical");

    public val filterName: String
        get() = "filter_$value"

    public val bigIconUrl: String get() = getStaticFileUrl("images", "global", "content", "newsicon_${value}_big.gif")
    public val smallIconUrl: String
        get() = getStaticFileUrl(
            "images",
            "global",
            "content",
            "newsicon_${value}_small.gif"
        )

    public companion object {
        private val iconRegex = Regex("""newsicon_([^_]+)_(?:small|big)""")
        public fun fromIcon(iconUrl: String): NewsCategory? {
            iconRegex.find(iconUrl)?.apply {
                return StringEnum.fromValue(this.groups[1]!!.value.lowercase())
            }
            return null
        }
    }
}
