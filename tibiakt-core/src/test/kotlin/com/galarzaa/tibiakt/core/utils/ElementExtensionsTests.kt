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

package com.galarzaa.tibiakt.core.utils

import com.galarzaa.tibiakt.TestResources.getResource
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.jsoup.Jsoup

class ElementExtensionsTests : StringSpec({
    "Parse a form with checkboxes"{
        val content: String = getResource("utils/formWithCheckboxes.txt")
        val formElement = Jsoup.parse(content).selectFirst("form")
        val formData = formElement!!.formData()
        formData.values["filter_begin_day"] shouldBe "25"
    }
})
