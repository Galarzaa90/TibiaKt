package com.galarzaa.tibiakt.enums

enum class HighscoresBattlEyeType(override val value: Int) : IntEnum {
    ANY_WORLD(-1),
    UNPROTECTED(0),
    PROTECTED(1),
    INITIALLY_PROTECTED(2),
}