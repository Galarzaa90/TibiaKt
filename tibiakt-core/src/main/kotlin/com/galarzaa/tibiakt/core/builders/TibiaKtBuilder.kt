package com.galarzaa.tibiakt.core.builders

/** Interface */
public abstract class TibiaKtBuilder<T> {
    /** Builds an instance with the current data in the builder.
     *
     * @throws IllegalStateException if the builder is missing required properties
     */
    public abstract fun build(): T
}
