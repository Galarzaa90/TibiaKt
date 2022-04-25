# Changelog

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