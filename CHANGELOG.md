# Changelog

## 2.0.0 (Unreleased)

- Reorganized package names. Classes are organized similar to sections on Tibia.com
- Renamed `BaseCharacter` to `TibiaCharacter`.
- Renamed `Character` to `CharacterInfo` to avoid conflicts with `java.lang.Character`
- Renamed `CharacterLevel` to `LevelAware`.
- `TibiaKtClient` now implements `TibiaKtApi` interface.
  - Remove `fetchNewsArchive` using days.
  - `fetchNews` renamed to `fetchNewsArticleById`.
  - Removed `fetchEventsSchedule` overloads with separate `year`  and `month` parameters.
- Added `displayName` property to `NewsCategory` enum.
- `NewsType.value` renamed to `displayName`, added `value` property to represent internal value.
- Renamed `News` to `NewsArticle`.
- Renamed `BaseCreatureEntry` to `BaseCreature`.
- Renamed `OtherCharacter` to `AccountCharacter`.
- Renamed `OtherCharacter.isDeleted` to `isScheduledForDeletion`.
- Renamed `Death.timestamp` to `occurredAt`.
- `DeathParticipant` is now a sealed interface with subtypes `Creature`, `Player` and `Summon`.
- All builder classes are now internal.

## 1.0.0 (2025/10/17)

- Initial release
- Added support for weapon proficiency, bestiary mastery, fragment progression and charm changes.
- Re-organized many of the package names.

## 0.12.1 (2025-07-08)

- Fix parsing loyalty points highscores not parsing character names correctly.

## 0.12.0 (2025-05-02)

- Fix Boosted Boss page structure on tibia.com and its parsing.

## 0.11.0 (2025-04-08)

- Added Monk to Vocation enums, including Highscores and Auction filters.

# 0.10.0 (2025-04-02)

- Fix parsing for character deaths due to changes released by CipSoft on April 1st 2025
- Upgrade from Kotlin 2.0.21 to 2.1.10

## 0.9.0 (2024-11-10)

- Upgrade to Ktor 3.

## 0.8.1 (2024-08-11)

- The icons used for news categories are now PNG images instead of GIF.

## 0.8.0 (2024-07-31)

- Added `isCachingEnabled` property to `TibiaResponse`
  - `isCached` tells us if the obtained content is fresh or cached.
  - `isCachingEnabled` tells us if the content's origin uses caching at all or not.
- Added `BaseForumThread` interface, extracting common properties from `ForumThread` and `ThreadEntry`.
- (**Breaking Change**) Removed `categoryIcon` property from
- (**Breaking Change**) The `queryParam` constant various enum used for filtering had has been renamed to `QUERY_PARAM`.
  - The same has been done to variations of this name, to an all uppercase name.

## 0.7.0 (2024-07-04)

- Fix auction parsing breaking due to new Animus Masteries unlocked field.
- Added `animusMasteriesUnlocked` field to `AuctionDetails`
- (**Breaking Change**) Revealed gems now properly parse multiple effects per mod.

## 0.6.2 (2024-05-25)

- Add parsing for revealed gems to auctions.

## 0.6.1 (2024-03-17)

- Fixed house and guildhall rents not being parsed correctly.

## 0.6.0 (2023-09-10)

- Fix character badges not being parsed correctly
- Added support for bonus promotion points for auctions.
- Renamed `Leaderboards` to `Leaderboard`
- Renamed `LeaderboardsEntry` to `LeaderboardEntry`
- Renamed `LeaderboardsRotation` to `LeaderboardRotation`
- Renamed `Character.characters` to `otherCharacters`
- `Character.accountStatus` is now a boolean instead of an enum, named `isPremium`
- `AccountStatus` enum is now deprecated.
- `AccountInformation.tutorStars` removed.
- Renamed `Killer` to `DeathParticipant`.
- Renamed `DeathParticipant.traded` to `isTraded`.
- Remove `WorldOverview.tournamentWorlds`.
- Renamed `GuildMember.joiningDate` to `joinedOn`
- Renamed `GuildInvite.inviteDate` to `invitedOn`
- Renamed `Highscores.lastUpdate` to `lastUpdated`
- Renamed `House.Rented.movingDate` to `transferDate`
- Renamed `LeaderboardRotation.current` to `isCurrent`

## v0.5.0 (2022-03-15)

- Added upgrade tier to item in auctions.
- Renamed `BosstableBosses` to `BoostableBosses` and all related classes.
- Upgrade to Kotlin 1.8.10

## v0.4.2 (2022-09-29)

- Fixed auctions not being parsed for daily rewards streaks over 1000.

## v0.4.1 (2022-09-28)

- Fixed incorrect URL building for forum sections.

## v0.4.0 (2022-09-12)

- `House` is now a sealed class with subtypes `Rented` and `Auctioned` for a cleaner model.
- Renamed all boolean properties to start with `is`, `has` or `are`.

## v0.3.2 (2022-09-05)

- Added configuration options to TibiaKtClient.

## v0.3.1 (2022-08-23)

- Removed request timeout.

## v0.3.0 (2022-08-17)

- Added `exaltedDust`, `bossPoints` and `bosstiaryProgress` to Auctions.
- Added boostable bosses parsing.
- Parse rotation information for Leaderboards.
- Use zone instead of offsets to parse dates.
- Added `BOSS_POINTS` highscores category.
- Replace Java's `Duration` and `Instant` with Kotlin's.
- Added handling for Tibia.com's site maintenance.
- Handle `GuildsSection` for worlds that don't exist.
- Handle `KillStatistics` for worlds that don't exist.
- Handle `Highscores` for worlds that don't exist.
- Properly build URL for `EventsSchedule`.
- Various parsing bugs.

## v0.2.0 (2022-04-22)

- Changed `Float` types to `Double`, easier to handle, doesn't require to suffix `f` to literals.
- Rewrote all builder classes to [type-safe builder pattern](https://kotlinlang.org/docs/type-safe-builders.html)
- Added parsing for the Tibia.com forums.
- Removed default values from many models.
- Fixed various parsing bugs.

## v0.1.0 (2020-01-24)

Available features:

- Parsing of the following Tibia.com sections
  - Characters
  - World Overview
  - Individual worlds (online player lists)
  - Guild lists
    - Individual guilds (no wars)
    - House lists
    - Individual houses
    - Character Bazaar
    - Auctions
    - Creatures Section (boosted creature and list only)
    - News Archive
    - Individual news
    - CM Posts
    - Highscores
    - Leaderboard
- Ktor based HTTP client with various methods to fetch from Tibia.com
