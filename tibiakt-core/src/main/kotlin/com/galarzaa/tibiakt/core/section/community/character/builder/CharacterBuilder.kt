/*
 * Copyright © 2025 Allan Galarza
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

package com.galarzaa.tibiakt.core.section.community.character.builder

import com.galarzaa.tibiakt.core.builder.BuilderDsl
import com.galarzaa.tibiakt.core.builder.TibiaKtBuilder
import com.galarzaa.tibiakt.core.builder.requireField
import com.galarzaa.tibiakt.core.domain.character.Sex
import com.galarzaa.tibiakt.core.domain.character.Vocation
import com.galarzaa.tibiakt.core.section.community.character.model.AccountBadge
import com.galarzaa.tibiakt.core.section.community.character.model.AccountCharacter
import com.galarzaa.tibiakt.core.section.community.character.model.AccountInformation
import com.galarzaa.tibiakt.core.section.community.character.model.CharacterHouse
import com.galarzaa.tibiakt.core.section.community.character.model.CharacterInfo
import com.galarzaa.tibiakt.core.section.community.character.model.Death
import com.galarzaa.tibiakt.core.section.community.character.model.DeathParticipant
import com.galarzaa.tibiakt.core.section.community.character.model.DisplayedAchievement
import com.galarzaa.tibiakt.core.section.community.character.model.GuildMembership
import kotlinx.datetime.LocalDate
import kotlin.time.Instant

@BuilderDsl
public inline fun character(block: CharacterBuilder.() -> Unit): CharacterInfo = CharacterBuilder().apply(block).build()

@BuilderDsl
public inline fun characterBuilder(block: CharacterBuilder.() -> Unit): CharacterBuilder =
    CharacterBuilder().apply(block)

/** Builder for [CharacterInfo] instances. */
@BuilderDsl
public class CharacterBuilder : TibiaKtBuilder<CharacterInfo> {
    public lateinit var name: String
    public var level: Int = 2
    public var residence: String? = null
    public var vocation: Vocation? = null
    public var sex: Sex? = null
    public var world: String? = null
    public var achievementPoints: Int = 0
    public var lastLoginAt: Instant? = null
    public var isRecentlyTraded: Boolean = false
    public var formerNames: List<String> = emptyList()
    public var deletionScheduledAt: Instant? = null
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
    public val otherCharacters: MutableList<AccountCharacter> = mutableListOf()

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
        occurredAt: Instant,
        level: Int,
        killers: List<DeathParticipant>,
        assists: List<DeathParticipant>,
    ): CharacterBuilder = apply {
        deaths.add(Death(occurredAt, level, killers, assists))
    }

    public fun addOtherCharacter(
        name: String,
        world: String,
        isMain: Boolean = false,
        isOnline: Boolean = false,
        isDeleted: Boolean = false,
        isRecentlyTraded: Boolean = false,
        position: String?,
    ): CharacterBuilder = apply {
        otherCharacters.add(
            AccountCharacter(
                name, world, isMain, isOnline, isDeleted, isRecentlyTraded, position
            )
        )
    }

    override fun build(): CharacterInfo = CharacterInfo(
        name = requireField(::name.isInitialized, "name") { name },
        title = title,
        formerNames = formerNames,
        unlockedTitles = unlockedTitles,
        sex = requireField(sex, "sex"),
        vocation = requireField(vocation, "vocation"),
        level = level,
        achievementPoints = achievementPoints,
        world = requireField(world, "world"),
        formerWorld = formerWorld,
        residence = requireField(residence, "residence"),
        marriedTo = marriedTo,
        houses = houses,
        guildMembership = guildMembership,
        lastLoginAt = lastLoginAt,
        position = position,
        comment = comment,
        isPremium = isPremium,
        isRecentlyTraded = isRecentlyTraded,
        deletionScheduledAt = deletionScheduledAt,
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
            name = requireField(::name.isInitialized, "name") { name },
            houseId = houseId,
            town = requireField(::town.isInitialized, "town") { town },
            paidUntil = requireField(::paidUntil.isInitialized, "paidUntil") { paidUntil },
            world = requireField(::world.isInitialized, "world") { world },
        )
    }
}
