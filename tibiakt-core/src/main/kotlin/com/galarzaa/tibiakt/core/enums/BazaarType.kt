package com.galarzaa.tibiakt.core.enums

enum class BazaarType(override val value: String, val subtopic: String) : StringEnum {
    CURRENT("current", "currentcharactertrades"),
    HISTORY("history", "pastcharactertrades"),
}