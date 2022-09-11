package com.galarzaa.tibiakt.core.enums

public enum class BazaarType(override val value: String, public val subtopic: String) : StringEnum {
    CURRENT("current", "currentcharactertrades"),
    HISTORY("history", "pastcharactertrades"),
}
