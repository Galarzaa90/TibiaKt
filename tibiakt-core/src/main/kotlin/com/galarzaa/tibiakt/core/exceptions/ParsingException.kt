package com.galarzaa.tibiakt.core.exceptions

/**
 * Exception thrown when parsing failed.
 *
 * Parsing might fail when the wrong [com.galarzaa.tibiakt.core.parsers.Parser] was used for a specific HTML content.
 * Alternatively, it might fail when Tibia.com received changes.
 */
open class ParsingException(message: String? = null, cause: Throwable? = null) : TibiaKtException(message, cause)