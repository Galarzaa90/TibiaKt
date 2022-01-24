package com.galarzaa.tibiakt.core.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.YearMonth

/**
 * Serializer for [YearMonth] objects, using YYYY-MM format.
 */
object YearMonthSerializer : KSerializer<YearMonth> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("YearMonth", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: YearMonth) =
        encoder.encodeString(value.toString())

    override fun deserialize(decoder: Decoder): YearMonth =
        YearMonth.parse(decoder.decodeString())
}