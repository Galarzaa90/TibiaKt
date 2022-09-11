package com.galarzaa.tibiakt.core.enums

public interface IntEnum {
    public val value: Int

    public companion object {
        public inline fun <reified T> fromValue(value: String?): T? where T : Enum<T>, T : IntEnum =
            T::class.java.takeIf { it.isEnum }?.enumConstants?.find { it.value.toString() == value || it.name == value }

        public inline fun <reified T> fromValue(value: Int?): T? where T : Enum<T>, T : IntEnum =
            fromValue(value.toString())
    }
}
