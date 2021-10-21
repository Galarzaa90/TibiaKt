package com.galarzaa.tibiakt.core.enums

interface StringEnum {
    val value: String

    companion object {
        inline fun <reified T> fromValue(value: String?): T? where T : Enum<T>, T : StringEnum =
            T::class.java.takeIf { it.isEnum }?.enumConstants?.find { it.value == value || it.name == value }
    }
}