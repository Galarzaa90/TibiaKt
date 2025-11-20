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

package com.galarzaa.tibiakt.core.builder

/** A builder DSL. */
@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
public annotation class BuilderDsl


/** Interface for builders of TibiaKt models. */
internal fun interface TibiaKtBuilder<T> {
    /** Builds an instance with the current data in the builder.
     *
     * @throws IllegalStateException if the builder is missing required properties
     */
    fun build(): T
}


internal fun <T : Any> requireField(value: T?, name: String): T = requireNotNull(value) { "$name is required" }

internal inline fun <T> requireField(initialized: Boolean, name: String, value: () -> T): T =
    if (initialized) value() else error("$name is required")
