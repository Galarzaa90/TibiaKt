/*
 * Copyright © 2024 Allan Galarza
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

package com.galarzaa.tibiakt.core.builders

import com.galarzaa.tibiakt.core.enums.Sex
import com.galarzaa.tibiakt.core.enums.Vocation
import com.galarzaa.tibiakt.core.models.character.AccountBadge
import com.galarzaa.tibiakt.core.models.character.AccountInformation
import com.galarzaa.tibiakt.core.models.character.Character
import com.galarzaa.tibiakt.core.models.character.CharacterHouse
import com.galarzaa.tibiakt.core.models.character.Death
import com.galarzaa.tibiakt.core.models.character.DeathParticipant
import com.galarzaa.tibiakt.core.models.character.DisplayedAchievement
import com.galarzaa.tibiakt.core.models.character.GuildMembership
import com.galarzaa.tibiakt.core.models.character.OtherCharacter
import com.galarzaa.tibiakt.core.builders.BuilderDsl
import kotlin.time.Instant
import kotlinx.datetime.LocalDate

@BuilderDsl
public inline fun character(block: CharacterBuilder.() -> Unit): Character = CharacterBuilder().apply(block).build()

@BuilderDsl
public inline fun characterBuilder(block: CharacterBuilder.() -> Unit): CharacterBuilder =
    CharacterBuilder().apply(block)

/** Builder for [Character] instances. */
@BuilderDsl
public class CharacterBuilder : TibiaKtBuilder<Character> {
    public lateinit var name: String
    public var level: Int = 2
    public var residence: String? = null
    public var vocation: Vocation? = null
    public var sex: Sex? = null
    public var world: String? = null
    public var achievementPoints: Int = 0
    public var lastLogin: Instant? = null
    public var isRecentlyTraded: Boolean = false
    public var formerNames: List<String> = emptyList()
    public var deletionDate: Instant? = null
    public var formerWorld: String? = null
    public var isPremium: Boolean = false
    public var comment: String? = null
    public var title: String? = null
    public var position: String? = null
    public var unlockedTitles: Int = 0
    public var marriedTo: String? = null
    public val houses: MutableList<CharacterHouse> = mutableListOf()
    public var guildMembership: GuildMembership? = null
    public val accountBadges: MutableList<AccountBadge> = mutableListOf()
    public val achievements: MutableList<DisplayedAchievement> = mutableListOf()
    public var accountInformation: AccountInformation? = null
    public val deaths: MutableList<Death> = mutableListOf()
    public val otherCharacters: MutableList<OtherCharacter> = mutableListOf()

    public fun addHouse(
        name: String,
        houseId: Int,
        town: String,
        paidUntil: LocalDate,
        world: String,
    ): CharacterBuilder = apply {
        houses.add(CharacterHouse(name, houseId, town, paidUntil, world))
    }

    @BuilderDsl
    public fun house(block: CharacterHouseBuilder.() -> Unit): CharacterBuilder =
        apply { houses.add(CharacterHouseBuilder().apply(block).build()) }

    public fun addBadge(name: String, descroption: String, iconUrl: String): CharacterBuilder = apply {
        accountBadges.add(AccountBadge(name, descroption, iconUrl))
    }

    public fun addAchievement(name: String, grade: Int, isSecret: Boolean): CharacterBuilder = apply {
        achievements.add(DisplayedAchievement(name, grade, isSecret))
    }

    public fun accountInformation(
        created: Instant,
        loyaltyTitle: String?,
        position: String?,
    ): CharacterBuilder = apply {
        accountInformation = AccountInformation(created, loyaltyTitle, position)
    }

    public fun guild(rank: String, guild: String): CharacterBuilder =
        apply { guildMembership = GuildMembership(guild, rank) }

    public fun addDeath(
        timestamp: Instant,
        level: Int,
        killers: List<DeathParticipant>,
        assists: List<DeathParticipant>,
    ): CharacterBuilder = apply {
        deaths.add(Death(timestamp, level, killers, assists))
    }

    public fun addOtherCharacter(
        name: String,
        world: String,
        isMain: Boolean = false,
        isOnline: Boolean = false,
        isDeleted: Boolean = false,
        isRecentlyTraded: Boolean = false,
        position: String?,
    ): CharacterBuilder =
        apply {
            otherCharacters.add(
                OtherCharacter(
                    name,
                    world,
                    isMain,
                    isOnline,
                    isDeleted,
                    isRecentlyTraded,
                    position
                )
            )
        }

    override fun build(): Character = Character(
        name = if (::name.isInitialized) name else error("name is required"),
        title = title,
        formerNames = formerNames,
        unlockedTitles = unlockedTitles,
        sex = sex ?: error("sex is required"),
        vocation = vocation ?: error("vocation is required"),
        level = level,
        achievementPoints = achievementPoints,
        world = world ?: error("world is required"),
        formerWorld = formerWorld,
        residence = residence ?: error("residence is required"),
        marriedTo = marriedTo,
        houses = houses,
        guildMembership = guildMembership,
        lastLogin = lastLogin,
        position = position,
        comment = comment,
        isPremium = isPremium,
        isRecentlyTraded = isRecentlyTraded,
        deletionDate = deletionDate,
        badges = accountBadges,
        achievements = achievements,
        deaths = deaths,
        accountInformation = accountInformation,
        otherCharacters = otherCharacters
    )

    public class CharacterHouseBuilder : TibiaKtBuilder<CharacterHouse> {
        public lateinit var name: String
        public var houseId: Int = 0
        public lateinit var town: String
        public lateinit var paidUntil: LocalDate
        public lateinit var world: String
        public override fun build(): CharacterHouse = CharacterHouse(
            name = if (::name.isInitialized) name else error("name is required"),
            houseId = houseId,
            town = if (::town.isInitialized) town else error("town is required"),
            paidUntil = if (::paidUntil.isInitialized) paidUntil else error("paidUntil is required"),
            world = if (::world.isInitialized) world else error("world is required"),
        )
    }
}
