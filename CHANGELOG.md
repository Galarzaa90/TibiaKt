# Changelog

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
