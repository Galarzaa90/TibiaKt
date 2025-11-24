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

package com.galarzaa.tibiakt.client.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Container for responses returned by Tibia’s AJAX endpoints.
 *
 * The `AjaxObjects` field contains one or more elements wrapping HTML fragments
 * or auxiliary metadata used by website features such as character trades,
 * auctions, pagination, or filtering panels.
 *
 * Each entry is represented as an [AjaxObject].
 */
@Serializable
public data class AjaxResponse(
    /**
     * List of AJAX objects returned by the endpoint.
     *
     * Each object contains:
     * - a raw HTML fragment,
     * - a type identifier describing how the client should interpret the data,
     * - and the CSS selector or DOM target the Tibia website uses to inject it.
     */
    @SerialName("AjaxObjects")
    val ajaxObjects: List<AjaxObject>,
)

/**
 * Represents a single AJAX payload item returned by Tibia’s internal web services.
 *
 * @property data Raw HTML content provided by the endpoint.
 * This string typically contains `<div>`, `<img>`, and other markup used to
 * dynamically populate sections of the Tibia.com page (for example: lists of items,
 * pagination rows, character trade objects, or dialog content).
 *
 * @property dataType String indicating the kind of data contained in [data].
 * Common values include `"HTML"` or `"Container"`, informing the client-side
 * scripts how the content should be interpreted.
 *
 * @property target CSS selector or DOM target ID used by Tibia.com to insert the
 * provided HTML fragment into the page.
 */
@Serializable
public data class AjaxObject(
    @SerialName("Data")
    val data: String,

    @SerialName("DataType")
    val dataType: String,

    @SerialName("Target")
    val target: String,
)
