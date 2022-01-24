package com.galarzaa.tibiakt.client.exceptions

import com.galarzaa.tibiakt.core.exceptions.TibiaKtException

/**
 * Exception thrown when there was a network error trying to fetch a resource from the web.
 */
open class NetworkException(message: String? = null, cause: Throwable? = null) : TibiaKtException(message, cause)

