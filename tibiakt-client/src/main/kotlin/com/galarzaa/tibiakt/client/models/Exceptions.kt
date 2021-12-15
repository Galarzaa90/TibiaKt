package com.galarzaa.tibiakt.client.models

import com.galarzaa.tibiakt.core.utils.TibiaKtException

/**
 * Exception thrown when there was a network error trying to fetch a resource from the web.
 */
open class NetworkException(message: String? = null, cause: Throwable? = null) : TibiaKtException(message, cause)

/**
 *  Exception thrown when Tibia.com returns a 403 status code.
 *
 *  Tibia.com returns a 403 status code when it detects that too many requests are being done.
 *  This has its own subclass to let the user decide to treat this differently than other network errors.
 */
open class ForbiddenException(message: String? = null, cause: Throwable?) : NetworkException(message, cause)

/**
 * Exception thrown when Tibia.com is under maintenance.
 *
 * When Tibia.com is under maintenance, all sections of the website redirect to maintenance.tibia.com.
 */
open class SiteMaintenanceException(message: String?, cause: Throwable?) : NetworkException(message, cause)