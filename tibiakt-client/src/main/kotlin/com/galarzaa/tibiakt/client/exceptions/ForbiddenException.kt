package com.galarzaa.tibiakt.client.exceptions

/**
 *  Exception thrown when Tibia.com returns a 403 status code.
 *
 *  Tibia.com returns a 403 status code when it detects that too many requests are being done.
 *  This has its own subclass to let the user decide to treat this differently than other network errors.
 */
public open class ForbiddenException(message: String? = null, cause: Throwable?) : NetworkException(message, cause)
