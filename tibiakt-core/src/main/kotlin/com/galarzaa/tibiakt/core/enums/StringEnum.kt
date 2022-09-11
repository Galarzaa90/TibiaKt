package com.galarzaa.tibiakt.core.enums

public interface StringEnum {
    /**
     * The string representation of the enum.
     */
    public val value: String

    public companion object {
        public inline fun <reified T> fromValue(value: String?): T? where T : Enum<T>, T : StringEnum =
            T::class.java.takeIf { it.isEnum }?.enumConstants?.find { it.value == value || it.name == value }
    }
}
