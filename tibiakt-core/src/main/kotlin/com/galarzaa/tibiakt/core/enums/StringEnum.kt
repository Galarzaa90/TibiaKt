package com.galarzaa.tibiakt.core.enums

interface StringEnum {
    val value: String

    companion object {
        inline fun <reified T : StringEnum> fromValue(value: String?) =
            T::class.java.takeIf { it.isEnum }?.enumConstants?.find { it.value == value }

    }
}