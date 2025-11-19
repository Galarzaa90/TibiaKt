/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.tibiakt.core.net

import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.BazaarType
import com.galarzaa.tibiakt.core.section.charactertrade.model.BazaarFilters
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldContain

class BazaarUrlsTests : FunSpec({

    context("bazaarUrl") {
        test("Current auctions, no filters") {
            val url =
                _root_ide_package_.com.galarzaa.tibiakt.core.section.charactertrade.urls.bazaarUrl(BazaarType.CURRENT)

            url shouldContain "/charactertrade"
            url shouldContain "subtopic=${BazaarType.CURRENT.subtopic}"
            url shouldContain "currentpage=1"
        }

        test("Auction history, level range filter") {
            val url = _root_ide_package_.com.galarzaa.tibiakt.core.section.charactertrade.urls.bazaarUrl(
                BazaarType.HISTORY, BazaarFilters(
                    minimumLevel = 100,
                    maximumLevel = 200,
                )
            )

            url shouldContain "/charactertrade"
            url shouldContain "subtopic=${BazaarType.HISTORY.subtopic}"
            url shouldContain "filter_levelrangefrom=100"
            url shouldContain "filter_levelrangeto=200"
        }
    }

    test("auctionUrl") {
        val url = _root_ide_package_.com.galarzaa.tibiakt.core.section.charactertrade.urls.auctionUrl(12_345)

        url shouldContain "/charactertrade"
        url shouldContain "subtopic=currentcharactertrades"
        url shouldContain "auctionid=12345"
    }
})
