@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.character

import com.galarzaa.tibiakt.core.utils.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

/**
 * Information about an account.
 *
 * @property creation The date and time when the account was created.
 * @property loyaltyTitle The loyalty title of the account, if any.
 * @property position If the account holds a special position.
 * @property tutorStars The amount of stars the tutor has.
 */
@Serializable
data class AccountInformation(
    val creation: Instant,
    val loyaltyTitle: String?,
    val position: String? = null,
    val tutorStars: Int? = null
)