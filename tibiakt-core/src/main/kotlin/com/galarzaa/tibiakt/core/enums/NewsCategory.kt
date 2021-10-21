package com.galarzaa.tibiakt.core.enums

import com.galarzaa.tibiakt.core.utils.getStaticFileUrl

enum class NewsCategory(override val value: String) : StringEnum {
    CIPSOFT("cipsoft"),
    COMMUNITY("community"),
    DEVELOPMENT("development"),
    SUPPORT("support"),
    TECHNICAL_ISSUES("technical");

    val filterName
        get() = "filter_$value"

    val bigIconUrl get() = getStaticFileUrl("images", "global", "content", "newsicon_${value}_big.gif")
    val smallIconUrl get() = getStaticFileUrl("images", "global", "content", "newsicon_${value}_small.gif")

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