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

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain


class UrlsTests : FunSpec({
    context("tibiaUrl") {


        test("base host, simple query, no anchor") {
            val url = tibiaUrl("news", "id" to 123)

            url shouldContain "https://$BASE_HOST/news/"
            url shouldContain "?id=123"
            url shouldNotContain "#"
        }

        test("test host and anchor") {
            val url = tibiaUrl("community", "world" to "Antica", test = true, anchor = "section")

            url shouldContain "https://$TEST_HOST/community/"
            url shouldContain "?world=Antica"
            url shouldContain "#section"
        }

        test("filters null params and encodes values") {
            val url = tibiaUrl(
                "community",
                "name" to "Foo/Bar Baz",
                "unused" to null
            )

            url shouldContain "name=Foo%2FBar+Baz"
            url shouldNotContain "unused="
        }

        test("trims trailing slash from section and ensures single slash") {
            val url = tibiaUrl("news/", "id" to 1)

            url shouldContain "https://$BASE_HOST/news/"
        }

        // tibiaUrl(section, subtopic, vararg params, test, anchor)
        test("with subtopic prepends subtopic param") {
            val url = tibiaUrl(
                "community", "highscores",
                "world" to "Antica",
                "category" to 1
            )

            url shouldContain "https://$BASE_HOST/community/"
            url shouldContain "subtopic=highscores"
            (url.indexOf("subtopic=highscores") < url.indexOf("world=Antica")) shouldBe true
            url.indexOf("?subtopic=highscores") shouldBe url.indexOf("?subtopic=highscores") // ensures it's in query
        }

        test("with subtopic on test host and anchor") {
            val url = tibiaUrl(
                "forum", "forum",
                "action" to "cm_post_archive",
                test = true,
                anchor = "post42"
            )

            url shouldContain "https://$TEST_HOST/forum/"
            url shouldContain "subtopic=forum"
            url shouldContain "action=cm_post_archive"
            url shouldContain "#post42"
        }
    }

    context("staticFileUrl") {

        test("normalizes double slashes") {
            val url = staticFileUrl("images//creatures//dragon.gif")

            url shouldContain "https://$STATIC_HOST/images/creatures/dragon.gif"
            url shouldNotContain "//images"
            url shouldNotContain "creatures//"
        }

        test("test host prefix") {
            val url = staticFileUrl("ui/icons/sword.png", test = true)

            url shouldContain "https://test.$STATIC_HOST/ui/icons/sword.png"
        }

        test("vararg joins with slashes") {
            val url = staticFileUrl("images", "creatures", "dragon.gif")

            url shouldContain "https://$STATIC_HOST/images/creatures/dragon.gif"
        }

        test("vararg with test host") {
            val url = staticFileUrl("images", "items", "shield.png", test = true)

            url shouldContain "https://test.$STATIC_HOST/images/items/shield.png"
        }
    }
})
