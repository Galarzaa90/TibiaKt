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

package com.galarzaa.tibiakt.core.models.creatures

import com.galarzaa.tibiakt.core.utils.getStaticFileUrl

/**
 * Base interface for creatures in the library.
 *
 * @property name The name of the creature.
 * @property identifier The internal name of the creature, used for images and links (where applicable).
 */
public interface BaseCreatureEntry {
    public val name: String
    public val identifier: String

    /**
     * The URL to the creature's image.
     */
    public val imageUrl: String get() = getStaticFileUrl("images", "library", "$identifier.gif")
}
