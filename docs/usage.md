# Usage

TibiaKt comes in two different packages, and a runnable application, to be used according to your needs.

## tibiakt-core

This is the heart of TibiaKt, it contains all the model definitions and parsing logic.

You can use this to parse the HTML content of Tibia pages obtained with any library.

```kotlin
val characterUrl: String = characterUrl("Galarzaa Fidera")
val content: String = client.get(characterUrl) // Replace with any HTTP library you want
val character: Character = CharacterParser.fromContent(content)
```

## tibiakt-client

A ready to use client based on [ktor](https://ktor.io/).

All you need is to create an instance of `TibiaKtClient` and you're ready to go!

```kotlin
val client = TibiaKtClient()

val character: TibiaResponse<Character> = client.fetchCharacter("Galarzaa Fidera")
val world: TibiaResponse<World> = client.fetchWorld("Gladera")
```

The `TibiaResponse` object contains information such as the time it took to fetch the data, the time it took to parse it
and whether the data is fresh or cached.

## tibiakt-server

A standalone application that deploys an HTTP server to provide endpoints to serve Tibia data in JSON format.

### Docker

A [Dockerfile](Dockerfile) is also available, or it can be pulled
from [Docker Hub](https://hub.docker.com/repository/docker/galarzaa90/tibiakt)   

[![Docker Image Size (latest by date)](https://img.shields.io/docker/image-size/galarzaa90/tibiakt?sort=semver)](https://hub.docker.com/repository/docker/galarzaa90/tibiakt)
[![Docker Image Version (latest semver)](https://img.shields.io/docker/v/galarzaa90/tibiakt?sort=semver)](https://hub.docker.com/repository/docker/galarzaa90/tibiakt)
[![Docker Pulls](https://img.shields.io/docker/pulls/galarzaa90/tibiakt)](https://hub.docker.com/repository/docker/galarzaa90/tibiakt)

```shell
docker pull galarzaa90/tibiakt
docker run --rm -ti -p 8080:8080 galarzaa90/tibiakt
```

#### Example

**GET** `<baseUrl>/characters/Galarzaa+Fidera`

```json
{
  "timestamp": "2022-01-23T16:57:26.275Z",
  "isCached": false,
  "cacheAge": 0,
  "isCachingEnabled": true,
  "fetchingTime": 0.271,
  "parsingTime": 0.016,
  "data": {
    "name": "Galarzaa Fidera",
    "title": null,
    "formerNames": [],
    "unlockedTitles": 12,
    "sex": "MALE",
    "vocation": "ROYAL_PALADIN",
    "level": 287,
    "achievementPoints": 410,
    "world": "Gladera",
    "formerWorld": null,
    "residence": "Thais",
    "marriedTo": "Auron Freezecaster",
    "houses": [],
    "guildMembership": {
      "name": "Bald Dwarfs",
      "rank": "Ironbreaker"
    },
    "lastLogin": "2022-01-08T16:21:10Z",
    "position": null,
    "comment": "03/11/15 - Character created\n09/01/16 - Level 100\n07/02/16 - Mortal Combat (Level 122)\n29/02/16 - Cave Explorer Outfit (Level 135)\n04/04/16 - Annihilator Quest (Level 152) with Nezune, Miraxe and Ir Yut\n01/04/19 - Nezune deleted by almighty and completely accurate CipSoft detection tool.\n\n RIP Nezune - Don't get hunted by Pablinn in Deletera \n\nNabBot's support discord server:\nhttps://support.nabbot.xyz/\n\n/NB-23FC13AC7400000/",
    "accountStatus": "PREMIUM_ACCOUNT",
    "recentlyTraded": false,
    "deletionDate": null,
    "badges": [
      {
        "name": "Ancient Hero",
        "description": "The account is older than 15 years.",
        "imageUrl": "https://static.tibia.com/images//badges/badge_accountage4.png"
      },
      {
        "name": "Senior Hero",
        "description": "The account is older than 10 years.",
        "imageUrl": "https://static.tibia.com/images//badges/badge_accountage3.png"
      },
      {
        "name": "Veteran Hero",
        "description": "The account is older than 5 years.",
        "imageUrl": "https://static.tibia.com/images//badges/badge_accountage2.png"
      },
      {
        "name": "Fledgeling Hero",
        "description": "The account is older than 1 year.",
        "imageUrl": "https://static.tibia.com/images//badges/badge_accountage1.png"
      },
      {
        "name": "Tibia Loyalist (Grade 2)",
        "description": "The account earned more than 1000 loyalty points.",
        "imageUrl": "https://static.tibia.com/images//badges/badge_tibialoyalist2.png"
      },
      {
        "name": "Tibia Loyalist (Grade 1)",
        "description": "The account earned more than 100 loyalty points.",
        "imageUrl": "https://static.tibia.com/images//badges/badge_tibialoyalist1.png"
      },
      {
        "name": "Global Player (Grade 1)",
        "description": "Summing up the levels of all characters on the account amounts to at least 500.",
        "imageUrl": "https://static.tibia.com/images//badges/badge_globalplayer1.png"
      },
      {
        "name": "Freshman of the Tournament",
        "description": "The account participated 1x in a Tournament.",
        "imageUrl": "https://static.tibia.com/images//badges/badge_tournamentparticipance1.png"
      }
    ],
    "achievements": [
      {
        "name": "Cave Completionist",
        "grade": 1,
        "secret": false
      },
      {
        "name": "Dream's Over",
        "grade": 1,
        "secret": false
      },
      {
        "name": "Friend of the Apes",
        "grade": 2,
        "secret": false
      },
      {
        "name": "True Lightbearer",
        "grade": 2,
        "secret": true
      },
      {
        "name": "Warlord of Svargrond",
        "grade": 2,
        "secret": false
      }
    ],
    "deaths": [],
    "accountInformation": {
      "creation": "2004-05-22T04:35:43Z",
      "loyaltyTitle": "Squire of Tibia",
      "position": null,
      "tutorStars": null
    },
    "characters": [
      {
        "name": "Don Heron",
        "world": "Antica",
        "main": false,
        "isOnline": false,
        "isDeleted": false,
        "recentlyTraded": false,
        "position": null
      },
      {
        "name": "Eggplant Galarzaa",
        "world": "Secura",
        "main": false,
        "isOnline": false,
        "isDeleted": false,
        "recentlyTraded": false,
        "position": null
      },
      {
        "name": "Galarzaa",
        "world": "Calmera",
        "main": false,
        "isOnline": false,
        "isDeleted": false,
        "recentlyTraded": false,
        "position": null
      },
      {
        "name": "Galarzaa Deathbringer",
        "world": "Gladera",
        "main": false,
        "isOnline": false,
        "isDeleted": false,
        "recentlyTraded": false,
        "position": null
      },
      {
        "name": "Galarzaa Fidera",
        "world": "Gladera",
        "main": true,
        "isOnline": false,
        "isDeleted": false,
        "recentlyTraded": false,
        "position": null
      },
      {
        "name": "Galarzaa Redd",
        "world": "Gladera",
        "main": false,
        "isOnline": false,
        "isDeleted": false,
        "recentlyTraded": false,
        "position": null
      },
      {
        "name": "Galarzaa The Druid",
        "world": "Gladera",
        "main": false,
        "isOnline": false,
        "isDeleted": false,
        "recentlyTraded": false,
        "position": null
      },
      {
        "name": "Gallan Doente",
        "world": "Belobra",
        "main": false,
        "isOnline": false,
        "isDeleted": false,
        "recentlyTraded": false,
        "position": null
      },
      {
        "name": "Lord de los Druidas",
        "world": "Calmera",
        "main": false,
        "isOnline": false,
        "isDeleted": false,
        "recentlyTraded": false,
        "position": null
      },
      {
        "name": "Porfirio Diaz Mori",
        "world": "Tournament - restricted Store",
        "main": false,
        "isOnline": false,
        "isDeleted": false,
        "recentlyTraded": false,
        "position": null
      },
      {
        "name": "Sir Galarzaa",
        "world": "Calmera",
        "main": false,
        "isOnline": false,
        "isDeleted": false,
        "recentlyTraded": false,
        "position": null
      },
      {
        "name": "Sir Heron",
        "world": "Zunera",
        "main": false,
        "isOnline": false,
        "isDeleted": false,
        "recentlyTraded": false,
        "position": null
      }
    ]
  }
}
```
