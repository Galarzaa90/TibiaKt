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

package com.galarzaa.tibiakt.core.models.character

import kotlinx.serialization.Serializable

/**
 * A displayed account badge.
 *
 * @property name The name of the badge.
 * @property description A brief description of how the badge is obtained.
 * @property imageUrl The URL to the badge's image.
 */
@Serializable
public data class AccountBadge(val name: String, val description: String, val imageUrl: String)
