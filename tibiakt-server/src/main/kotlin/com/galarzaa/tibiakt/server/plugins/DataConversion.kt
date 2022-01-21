package com.galarzaa.tibiakt.server.plugins

import com.galarzaa.tibiakt.core.enums.AuctionBattlEyeFilter
import com.galarzaa.tibiakt.core.enums.AuctionOrderBy
import com.galarzaa.tibiakt.core.enums.AuctionOrderDirection
import com.galarzaa.tibiakt.core.enums.AuctionSearchType
import com.galarzaa.tibiakt.core.enums.AuctionSkillFilter
import com.galarzaa.tibiakt.core.enums.AuctionVocationFilter
import com.galarzaa.tibiakt.core.enums.BazaarType
import com.galarzaa.tibiakt.core.enums.HighscoresCategory
import com.galarzaa.tibiakt.core.enums.HighscoresProfession
import com.galarzaa.tibiakt.core.enums.HouseOrder
import com.galarzaa.tibiakt.core.enums.HouseStatus
import com.galarzaa.tibiakt.core.enums.HouseType
import com.galarzaa.tibiakt.core.enums.IntEnum
import com.galarzaa.tibiakt.core.enums.NewsCategory
import com.galarzaa.tibiakt.core.enums.NewsType
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.enums.StringEnum
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.DataConversion
import io.ktor.features.DelegatingConversionService
import io.ktor.util.DataConversionException
import java.time.LocalDate
import java.time.format.DateTimeFormatter


internal fun Application.configureDataConversion() {
    install(DataConversion) {
        convert<LocalDate> {
            val format = DateTimeFormatter.ISO_DATE
            decode { values, _ ->
                values.singleOrNull()?.let { LocalDate.parse(it, format) }
            }
            encode { value ->
                when (value) {
                    null -> listOf()
                    is LocalDate -> listOf(format.format(value))
                    else -> throw DataConversionException("Cannot convert $value as LocalDate")
                }
            }
        }
        convert<NewsType> { stringEnumConverter<NewsType>() }
        convert<NewsCategory> { stringEnumConverter<NewsCategory>() }

        convert<HouseType> { stringEnumConverter<HouseType>() }
        convert<HouseStatus> { stringEnumConverter<HouseStatus>() }
        convert<HouseOrder> { stringEnumConverter<HouseOrder>() }
        convert<HighscoresCategory> { intEnumConverter<HighscoresCategory>() }
        convert<HighscoresProfession> { intEnumConverter<HighscoresProfession>() }

        convert<BazaarType> { stringEnumConverter<BazaarType>() }
        convert<PvpType> { stringEnumConverter<PvpType>() }
        convert<AuctionBattlEyeFilter> { intEnumConverter<AuctionBattlEyeFilter>() }
        convert<AuctionSkillFilter> { intEnumConverter<AuctionSkillFilter>() }
        convert<AuctionVocationFilter> { intEnumConverter<AuctionVocationFilter>() }
        convert<AuctionOrderBy> { intEnumConverter<AuctionOrderBy>() }
        convert<AuctionOrderDirection> { intEnumConverter<AuctionOrderDirection>() }
        convert<AuctionSearchType> { intEnumConverter<AuctionSearchType>() }
    }
}

private inline fun <reified T> DelegatingConversionService.stringEnumConverter() where T : Enum<T>, T : StringEnum {
    decode { values, _ -> values.singleOrNull()?.let { StringEnum.fromValue<T>(it) } }
    encode { value ->
        when (value) {
            null -> listOf()
            is LocalDate -> listOf((value as StringEnum).value)
            else -> throw DataConversionException("Cannot convert $value as ${T::class.simpleName}")
        }
    }
}

private inline fun <reified T> DelegatingConversionService.intEnumConverter() where T : Enum<T>, T : IntEnum {
    decode { values, _ -> values.singleOrNull()?.let { IntEnum.fromValue<T>(it) } }
    encode { value ->
        when (value) {
            null -> listOf()
            is LocalDate -> listOf((value as IntEnum).value.toString())
            else -> throw DataConversionException("Cannot convert $value as ${T::class.simpleName}")
        }
    }
}