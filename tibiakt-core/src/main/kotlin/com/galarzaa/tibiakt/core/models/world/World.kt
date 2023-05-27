/*
 * Copyright © 2023 Allan Galarza
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

@file:UseSerializers(LocalDateSerializer::class, YearMonthSerializer::class)

package com.galarzaa.tibiakt.core.models.world

import com.galarzaa.tibiakt.core.enums.BattlEyeType
import com.galarzaa.tibiakt.core.enums.PvpType
import com.galarzaa.tibiakt.core.enums.TransferType
import com.galarzaa.tibiakt.core.serializers.LocalDateSerializer
import com.galarzaa.tibiakt.core.serializers.YearMonthSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate
import java.time.YearMonth

/**
 * A game world.
 *
 * @property isOnline Whether this world is currently online or not.
 * @property onlineCount The total of online players in this world.
 * @property location The geographical location of the server.
 * @property pvpType The type of PvP in this world.
 * @property battlEyeType The type of BattlEye protection present in this world.
 * @property battlEyeStartDate The date when BattlEye was implemented in this world.
 * @property transferType The type of transfer restrictions this world has.
 * @property isPremiumRestricted Whether the world can only be played by premium account characters or not.
 * @property isExperimental Whether the world is experimental or not.
 * @property onlineRecordCount The maximum online players recorded on this world.
 * @property onlineRecordDateTime The date and time when the maximum online players record was set.
 * @property creationDate The year and month when this world was created.
 * @property worldQuests The list of world quests completed.
 * @property playersOnline The list of online players in this world.
 */
@Serializable
public data class World(
    override val name: String,
    val isOnline: Boolean,
    val onlineCount: Int,
    val location: String,
    val pvpType: PvpType,
    val battlEyeType: BattlEyeType,
    val battlEyeStartDate: LocalDate?,
    val transferType: TransferType,
    val isPremiumRestricted: Boolean,
    val isExperimental: Boolean,
    val onlineRecordCount: Int,
    val onlineRecordDateTime: Instant,
    val creationDate: YearMonth,
    val worldQuests: List<String> ,
    val playersOnline: List<OnlineCharacter>,
) : BaseWorld {

    /** Check if a character from this world can be transferred to [target] world. */
    private fun transferableTo(target: World): Boolean {
        return pvpType.weight >= target.pvpType.weight &&
                isExperimental == target.isExperimental &&
                transferType != TransferType.LOCKED &&
                target.transferType != TransferType.BLOCKED &&
                battlEyeType.weight >= target.battlEyeType.weight
    }

    /** Check if a character from the [origin] world can transfer to this world. */
    public fun transferableFrom(origin: World): Boolean = origin.transferableTo(this)
}
