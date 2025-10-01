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

package com.galarzaa.tibiakt.core.models.bazaar

import com.galarzaa.tibiakt.core.net.staticFileUrl
import kotlinx.serialization.Serializable

/**
 * The mounts owned by an [Auction] character.
 */
@Serializable
public data class Mounts(
    override val currentPage: Int,
    override val totalPages: Int,
    override val resultsCount: Int,
    override val entries: List<MountEntry>,
    override val isFullyFetched: Boolean,
) : AjaxPaginator<MountEntry>

/**
 * A mount in an [Auction] character.
 *
 * @property name The name of the mount.
 * @property mountId The internal ID of the mount.
 */
@Serializable
public data class MountEntry(
    val name: String,
    val mountId: Int,
) {
    /**
     * The URL to the mount's image.
     */
    val imageUrl: String get() = staticFileUrl("images", "charactertrade", "mounts", "$mountId.gif")
}
