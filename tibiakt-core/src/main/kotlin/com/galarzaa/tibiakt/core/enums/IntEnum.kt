package com.galarzaa.tibiakt.core.enums

interface IntEnum {
    val value: Int

    companion object {
        inline fun <reified T> fromValue(value: String?): T? where T : Enum<T>, T : IntEnum =
            T::class.java.takeIf { it.isEnum }?.enumConstants?.find { it.value.toString() == value || it.name == value }

        inline fun <reified T> fromValue(value: Int?): T? where T : Enum<T>, T : IntEnum = fromValue(value.toString())
    }
}
