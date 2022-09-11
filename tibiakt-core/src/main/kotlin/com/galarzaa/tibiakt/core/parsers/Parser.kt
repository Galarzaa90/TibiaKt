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

package com.galarzaa.tibiakt.core.parsers

import com.galarzaa.tibiakt.core.utils.boxContent
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

internal interface Parser<T> {
    fun fromContent(content: String): T

    fun boxContent(content: String, parser: Parser? = null): Element {
        val document: Document = content.replaceFirst("ISO-8859-1", "utf-8")
            .let { if (parser == null) Jsoup.parse(it) else Jsoup.parse(it, "", parser) }
        return document.boxContent()
    }
}
