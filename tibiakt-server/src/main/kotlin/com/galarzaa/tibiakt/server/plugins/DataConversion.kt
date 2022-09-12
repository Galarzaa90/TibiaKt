/*
 * Copyright © 2022 Allan Galarza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
