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

package com.galarzaa.tibiakt.core.section.library.urls

import com.galarzaa.tibiakt.core.net.tibiaUrl

/**
 * URL to the Creatures section in Tibia.com.
 */
public fun creaturesUrl(): String = tibiaUrl("library", "creatures")


/**
 * URL to the Creatures section in Tibia.com.
 */
public fun boostableBossesUrl(): String = tibiaUrl("library", "boostablebosses")

/**
 * URL to a specific creature in Tibia.com.
 */
public fun creatureUrl(identifier: String): String = tibiaUrl("library", "creatures", "race" to identifier)
