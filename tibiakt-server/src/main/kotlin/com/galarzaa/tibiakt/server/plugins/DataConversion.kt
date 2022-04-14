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
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.dataconversion.DataConversion
import io.ktor.util.converters.DataConversionException
import io.ktor.util.converters.DelegatingConversionService


internal fun Application.configureDataConversion() {
    install(DataConversion) {
        convert<NewsType> { stringEnumConverter() }
        convert<NewsCategory> { stringEnumConverter() }

        convert<HouseType> { stringEnumConverter() }
        convert<HouseStatus> { stringEnumConverter() }
        convert<HouseOrder> { stringEnumConverter() }
        convert<HighscoresCategory> { intEnumConverter() }
        convert<HighscoresProfession> { intEnumConverter() }

        convert<BazaarType> { stringEnumConverter() }
        convert<PvpType> { stringEnumConverter() }
        convert<AuctionBattlEyeFilter> { intEnumConverter() }
        convert<AuctionSkillFilter> { intEnumConverter() }
        convert<AuctionVocationFilter> { intEnumConverter() }
        convert<AuctionOrderBy> { intEnumConverter() }
        convert<AuctionOrderDirection> { intEnumConverter() }
        convert<AuctionSearchType> { intEnumConverter() }
    }
}


private inline fun <reified T> DelegatingConversionService.Configuration<T>.stringEnumConverter() where T : Enum<T>, T : StringEnum {
    decode { v -> v.single().let { StringEnum.fromValue(it) } ?: throw DataConversionException("Invalid value") }
    encode { listOf((it as StringEnum).value) }
}

private inline fun <reified T> DelegatingConversionService.Configuration<T>.intEnumConverter() where T : Enum<T>, T : IntEnum {
    decode { v -> v.single().let { IntEnum.fromValue(it) } ?: throw DataConversionException("Invalid value") }
    encode { listOf((it as IntEnum).value.toString()) }
}