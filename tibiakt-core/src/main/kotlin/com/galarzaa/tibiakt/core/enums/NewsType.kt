package com.galarzaa.tibiakt.core.enums

public enum class NewsType(override val value: String) : StringEnum {
    NEWS("News"),
    NEWS_TICKER("News Ticker"),
    FEATURED_ARTICLE("Featured Article");

    public val filterName: String
        get() = "filter_$filterValue"

    public val filterValue: String
        get() = value.split(" ").last().lowercase()
}
