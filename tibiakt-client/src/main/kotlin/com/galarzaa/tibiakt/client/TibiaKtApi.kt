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

package com.galarzaa.tibiakt.client

import com.galarzaa.tibiakt.client.model.AjaxResponse
import com.galarzaa.tibiakt.client.model.TibiaResponse
import com.galarzaa.tibiakt.client.model.TimedResult
import com.galarzaa.tibiakt.core.domain.world.PvpType
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.Auction
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.AuctionPagesType
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.BazaarFilters
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.BazaarType
import com.galarzaa.tibiakt.core.section.charactertrade.bazaar.model.CharacterBazaar
import com.galarzaa.tibiakt.core.section.community.character.model.CharacterInfo
import com.galarzaa.tibiakt.core.section.community.guild.model.Guild
import com.galarzaa.tibiakt.core.section.community.guild.model.GuildsSection
import com.galarzaa.tibiakt.core.section.community.highscores.model.Highscores
import com.galarzaa.tibiakt.core.section.community.highscores.model.HighscoresBattlEyeType
import com.galarzaa.tibiakt.core.section.community.highscores.model.HighscoresCategory
import com.galarzaa.tibiakt.core.section.community.highscores.model.HighscoresProfession
import com.galarzaa.tibiakt.core.section.community.house.model.House
import com.galarzaa.tibiakt.core.section.community.house.model.HouseOrder
import com.galarzaa.tibiakt.core.section.community.house.model.HouseStatus
import com.galarzaa.tibiakt.core.section.community.house.model.HouseType
import com.galarzaa.tibiakt.core.section.community.house.model.HousesSection
import com.galarzaa.tibiakt.core.section.community.killstatistics.model.KillStatistics
import com.galarzaa.tibiakt.core.section.community.leaderboard.model.Leaderboard
import com.galarzaa.tibiakt.core.section.community.world.model.World
import com.galarzaa.tibiakt.core.section.community.world.model.WorldOverview
import com.galarzaa.tibiakt.core.section.forum.announcement.model.ForumAnnouncement
import com.galarzaa.tibiakt.core.section.forum.board.model.ForumBoard
import com.galarzaa.tibiakt.core.section.forum.cmpost.model.CMPostArchive
import com.galarzaa.tibiakt.core.section.forum.section.model.ForumSection
import com.galarzaa.tibiakt.core.section.forum.shared.model.AvailableForumSection
import com.galarzaa.tibiakt.core.section.forum.thread.model.ForumThread
import com.galarzaa.tibiakt.core.section.library.creature.model.BoostableBosses
import com.galarzaa.tibiakt.core.section.library.creature.model.CreaturesSection
import com.galarzaa.tibiakt.core.section.news.archive.model.NewsArchive
import com.galarzaa.tibiakt.core.section.news.article.model.NewsArticle
import com.galarzaa.tibiakt.core.section.news.event.model.EventsSchedule
import com.galarzaa.tibiakt.core.section.news.shared.model.NewsCategory
import com.galarzaa.tibiakt.core.section.news.shared.model.NewsType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth

public interface TibiaKtApi {

    // region News Section
    /**
     * Fetch the news for a given interval.
     */
    public suspend fun fetchNewsArchive(
        startAt: LocalDate,
        endAt: LocalDate,
        categories: Set<NewsCategory>? = null,
        types: Set<NewsType>? = null,
    ): TibiaResponse<NewsArchive>

    /**
     * Fetch a specific news article by its [newsId].
     */
    public suspend fun fetchNewsArticleById(newsId: Int): TibiaResponse<NewsArticle?>

    /**
     * Fetch the events schedule for the current month.
     */
    public suspend fun fetchEventsSchedule(): TibiaResponse<EventsSchedule>

    /**
     * Fetch the events schedule for a specific year and month.
     */
    public suspend fun fetchEventsSchedule(yearMonth: YearMonth): TibiaResponse<EventsSchedule>

    // endregion

    // region Library

    /** Fetch the boosted boss of the day as well as the list of bosstable bosses from Tibia.com. */
    public suspend fun fetchBoostableBosses(): TibiaResponse<BoostableBosses>

    /** Fetch the creatures section, containing the boosted creature. */
    public suspend fun fetchCreaturesSection(): TibiaResponse<CreaturesSection>
    // endregion

    // region Community


    /**
     * Fetch a character.
     *
     * @param name The name of the character.
     */
    public suspend fun fetchCharacter(name: String): TibiaResponse<CharacterInfo?>

    /**
     * Fetch the world overview, containing the list of worlds.
     */
    public suspend fun fetchWorldOverview(): TibiaResponse<WorldOverview>

    /** Fetch a world's information. */
    public suspend fun fetchWorld(name: String): TibiaResponse<World?>

    /** Fetch a guild by its [name]. */
    public suspend fun fetchGuild(name: String): TibiaResponse<Guild?>

    /** Fetch active and in formation guilds in a [world].
     *
     * If the world does not exist, it will return null.
     */
    public suspend fun fetchWorldGuilds(world: String): TibiaResponse<GuildsSection?>

    /**
     * Fetch the kill statistics for a [world].
     */
    public suspend fun fetchKillStatistics(world: String): TibiaResponse<KillStatistics?>

    /**
     * Fetch a page of the highscores.
     *
     * @param world The world to get highscores from. If null, the highscores of all worlds are returned.
     * @param category The category to fetch.
     * @param vocation The vocation to filter by. By default, all vocations will be returned
     * @param page The page number to fetch
     * @param battlEyeType The BattlEye type of the worlds to fetch. Only applies when [world] is null.
     * @param pvpTypes The PvP type of the worlds to fetch. Only applies when [world] is null.
     */
    public suspend fun fetchHighscoresPage(
        world: String?,
        category: HighscoresCategory,
        vocation: HighscoresProfession = HighscoresProfession.ALL,
        page: Int = 1,
        battlEyeType: HighscoresBattlEyeType = HighscoresBattlEyeType.ANY_WORLD,
        pvpTypes: Set<PvpType>? = null,
    ): TibiaResponse<Highscores?>


    /**
     * Fetch the houses section for a [world] and [town].
     */
    public suspend fun fetchHousesSection(
        world: String,
        town: String,
        type: HouseType? = null,
        status: HouseStatus? = null,
        order: HouseOrder? = null,
    ): TibiaResponse<HousesSection?>

    /** Fetch a house by its [houseId] in a specific world. */
    public suspend fun fetchHouse(
        houseId: Int,
        world: String,
    ): TibiaResponse<House?>

    /**
     * Fetches the Tibia Drome leaderboards for a [world].
     *
     * If the world does not exist, the leaderboards will be null.
     * @param rotation The rotation number to see. Tibia.com only allows viewing the current and last rotations. Any other value takes you to the current leaderboard.
     */
    public suspend fun fetchLeaderboard(
        world: String,
        rotation: Int? = null,
        page: Int = 1,
    ): TibiaResponse<Leaderboard?>

    // endregion

    // region Forum Section

    /**
     * Fetches a forum section by its internal [sectionId].
     */
    public suspend fun fetchForumSection(sectionId: Int): TibiaResponse<ForumSection?>

    /** Fetches a specific forum section. */
    public suspend fun fetchForumSection(section: AvailableForumSection): TibiaResponse<ForumSection?>

    /** Fetches a board from the Tibia.com forum.
     *
     * @param threadAge The maximum age of displayed threads. A value of -1 means no limit. Null means the server limit will be used.
     */
    public suspend fun fetchForumBoard(
        boardId: Int,
        page: Int = 1,
        threadAge: Int? = null,
    ): TibiaResponse<ForumBoard?>

    /** Fetches a thread from the Tibia.com forum. */
    public suspend fun fetchForumAnnouncement(announcementId: Int): TibiaResponse<ForumAnnouncement?>

    /** Fetches a forum thread from Tibia.com. */
    public suspend fun fetchForumThread(threadId: Int, page: Int = 1): TibiaResponse<ForumThread?>

    /** Fetches a forum thread containing the specified post.
     *
     * The thread will be fetched on the page containing the [ForumThread.anchoredPost]
     */
    public suspend fun fetchForumPost(postId: Int): TibiaResponse<ForumThread?>


    /** Fetch CM posts between two dates. */
    public suspend fun fetchCMPostArchive(
        startOn: LocalDate,
        endOn: LocalDate,
        page: Int = 0,
    ): TibiaResponse<CMPostArchive>

    // endregion

    // region Char Bazaar Section
    /**
     * Fetch the character bazaar.
     *
     * @param type Whether to show current auctions or the auction history.
     * @param filters The filtering parameters to use.
     * @param page The page to display.
     */
    public suspend fun fetchBazaar(
        type: BazaarType,
        filters: BazaarFilters? = null,
        page: Int = 1,
    ): TibiaResponse<CharacterBazaar>

    /**
     * Fetch an auction from Tibia.com.
     *
     * @param auctionId The ID of the auction to fetch.
     * @param skipDetails Whether to only fetch the auction's header and skip details.
     * @param fetchItems Whether to fetch items from further pages if necessary. Cannot be done if [skipDetails] is true.
     * @param fetchOutfits Whether to fetch outfits from further pages if necessary. Cannot be done if [skipDetails] is true.
     * @param fetchMounts Whether to fetch mounts from further pages if necessary. Cannot be done if [skipDetails] is true.
     */
    public suspend fun fetchAuction(
        auctionId: Int,
        skipDetails: Boolean = false,
        fetchItems: Boolean = false,
        fetchOutfits: Boolean = false,
        fetchMounts: Boolean = false,
    ): TibiaResponse<Auction?>

    /**
     * Fetch a single page from the auction pagination endpoint.
     */
    public suspend fun fetchAuctionDetailsPage(
        auctionId: Int,
        type: AuctionPagesType,
        page: Int,
    ): TimedResult<AjaxResponse>
    // endregion
}
