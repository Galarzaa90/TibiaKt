@file:UseSerializers(InstantSerializer::class)

package com.galarzaa.tibiakt.core.models.character

import com.galarzaa.tibiakt.core.serializers.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

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
    val position: String?,
    val tutorStars: Int?
)