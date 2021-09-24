package com.galarzaa.tibiakt.models

enum class NewsType(val value: String) {
    NEWS("News"),
    NEWS_TICKER("News Ticker"),
    FEATURED_ARTICLE("Featured Article");

    val filterName
        get() = "filter_${value.split(" ").last().lowercase()}"

    companion object {
        fun fromValue(value: String) = values().find { it.value == value }
    }
}