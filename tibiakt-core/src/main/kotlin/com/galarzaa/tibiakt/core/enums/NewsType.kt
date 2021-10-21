package com.galarzaa.tibiakt.core.enums

enum class NewsType(override val value: String) : StringEnum {
    NEWS("News"),
    NEWS_TICKER("News Ticker"),
    FEATURED_ARTICLE("Featured Article");

    val filterName
        get() = "filter_$filterValue"

    val filterValue
        get() = value.split(" ").last().lowercase()
}