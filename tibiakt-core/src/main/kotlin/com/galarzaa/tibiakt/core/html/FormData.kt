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

package com.galarzaa.tibiakt.core.html

/**
 * Contains the data extracted from a form.
 * @property values Mapping of form fields to their selected value.
 * @property valuesMultiple Mapping of form fields that might have multiple values.
 * @property availableOptions Mapping of the available options for selection in the form.
 * @property action Where the form would be submitted to.
 * @property method The HTTP method used
 */
internal data class FormData(
    val values: Map<String, String> = emptyMap(),
    val valuesMultiple: Map<String, List<String>> = emptyMap(),
    val availableOptions: Map<String, List<String>> = emptyMap(),
    val action: String? = null,
    val method: String? = null,
)
