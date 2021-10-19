package com.galarzaa.tibiakt.core.enums

interface IntEnum {
    val value: Int

    companion object {
        inline fun <reified T : IntEnum> fromValue(value: Int?) =
            T::class.java.takeIf { it.isEnum }?.enumConstants?.find { it.value == value }

        inline fun <reified T : IntEnum> fromValue(value: String?) =
            T::class.java.takeIf { it.isEnum }?.enumConstants?.find { it.value.toString() == value }

    }
}