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

package com.galarzaa.tibiakt.core.enums

/**
 * An enum with a string value.
 */
public interface StringEnum {
    /**
     * The string representation of the enum.
     */
    public val value: String

    public companion object {
        public inline fun <reified T> fromValue(value: String?): T? where T : Enum<T>, T : StringEnum =
            T::class.java.takeIf { it.isEnum }?.enumConstants?.find { it.value == value || it.name == value }
    }
}
