package com.galarzaa.tibiakt.core.enums

interface StringEnum {
    /**
     * The string representation of the enum.
     */
    val value: String

    companion object {
        inline fun <reified T> fromValue(value: String?): T? where T : Enum<T>, T : StringEnum =
            T::class.java.takeIf { it.isEnum }?.enumConstants?.find { it.value == value || it.name == value }
    }
}