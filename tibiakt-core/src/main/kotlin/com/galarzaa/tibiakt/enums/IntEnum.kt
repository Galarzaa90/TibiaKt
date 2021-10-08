package com.galarzaa.tibiakt.enums

interface IntEnum {
    val value: Int

    companion object {
        inline fun <reified T : IntEnum> fromValue(value: Int?) =
            T::class.java.takeIf { it.isEnum }?.enumConstants?.find { it.value == value }

    }
}