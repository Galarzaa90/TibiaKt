/*
 * Copyright © 2022 Allan Galarza
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
public data class AccountInformation(
    val creation: Instant,
    val loyaltyTitle: String?,
    val position: String?,
    val tutorStars: Int?,
)
