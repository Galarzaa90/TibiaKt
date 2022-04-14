package com.galarzaa.tibiakt.core.builders

/** Interface */
abstract class TibiaKtBuilder<T> {
    /** Builds an instance with the current data in the builder.
     *
     * @throws IllegalStateException if the builder is missing required properties
     */
    abstract fun build(): T
}