package com.galarzaa.tibiakt.client.exceptions

/**
 * Exception thrown when Tibia.com is under maintenance.
 *
 * When Tibia.com is under maintenance, all sections of the website redirect to maintenance.tibia.com.
 */
public open class SiteMaintenanceException(message: String?, cause: Throwable?) : NetworkException(message, cause)
