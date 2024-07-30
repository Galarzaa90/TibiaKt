/*
 * Copyright © 2024 Allan Galarza
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
 * An enum with a numeric value.
 */
public interface IntEnum {
    /**
     * The numeric value that represents the enum.
     */
    public val value: Int

    public companion object {
        /**
         * Get an instance of this that matches either the name or value of the entry.
         */
        public inline fun <reified T> fromValue(value: String?): T? where T : Enum<T>, T : IntEnum =
            T::class.java.takeIf { it.isEnum }?.enumConstants?.find { it.value.toString() == value || it.name == value }

        /**
         * Get an instance of this that matches the value of the entry.
         */
        public inline fun <reified T> fromValue(value: Int?): T? where T : Enum<T>, T : IntEnum =
            fromValue(value.toString())
    }
}
