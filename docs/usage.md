# Usage

## Parsing

The core feature of TibiaKT is the ability to parse Tibia HTML content into data. We bundle a client that does the
fetching and parsing for you, but if you prefer to implement your own client to add features like caching, rate
limiting, etc. you can do so.

```kotlin
val response = client.get("https://www.tibia.com/community/?subtopic=characters&name=Galarzaa+Fidera")
val htmlContent: String = response.body()
val character: Character = CharacterParser.fromContent(htmlContent)
```

`character` will be a `Character` object with all the information about the character. This can be even serialized into JSON.

??? example "Example JSON"
    ```json
    {
      "name": "Galarzaa Fidera",
      "title": "Gold Hoarder",
      "formerNames": [],
      "unlockedTitles": 14,
      "sex": "MALE",
      "vocation": "ROYAL_PALADIN",
      "level": 287,
      "achievementPoints": 410,
      "world": "Gladera",
      "formerWorld": null,
      "residence": "Svargrond",
      "marriedTo": "Auron Freezecaster",
      "houses": [
        {
          "name": "Trout Plaza 2",
          "houseId": 55112,
          "town": "Svargrond",
          "paidUntil": "2025-10-25",
          "world": "Gladera"
        }
      ],
      "guildMembership": {
        "name": "Bald Dwarfs",
        "rank": "Blacksmith"
      },
      "lastLoginAt": "2025-10-01T04:25:40Z",
      "position": null,
      "comment": "03/11/15 - Character created\n\r\n09/01/16 - Level 100\n\r\n07/02/16 - Mortal Combat (Level 122)\n\r\n29/02/16 - Cave Explorer Outfit (Level 135)\n\r\n04/04/16 - Annihilator Quest (Level 152) with Nezune, Miraxe and Ir Yut\n\r\n01/04/19 - Nezune deleted by almighty and completely accurate CipSoft detection tool.\n\r\n\n\r\n† RIP Nezune - Don't get hunted by Pablinn in Deletera †\n\r\n\n\r\nNabBot's support discord server:\n\r\nhttps://support.nabbot.xyz/\n\r\n\n\r\n/NB-23FC13AC7400000/",
      "isPremium": true,
      "isRecentlyTraded": false,
      "deletionScheduledAt": null,
      "badges": [
        {
          "name": "Exalted Hero",
          "description": "The account is older than 20 years.",
          "imageUrl": "https://static.tibia.com/images//badges/badge_accountage5.png"
        },
        {
          "name": "Tibia Loyalist (Grade 2)",
          "description": "The account earned more than 1000 loyalty points.",
          "imageUrl": "https://static.tibia.com/images//badges/badge_tibialoyalist2.png"
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
          "isSecret": false
        },
        {
          "name": "Dream's Over",
          "grade": 1,
          "isSecret": false
        },
        {
          "name": "Friend of the Apes",
          "grade": 2,
          "isSecret": false
        },
        {
          "name": "True Lightbearer",
          "grade": 2,
          "isSecret": true
        },
        {
          "name": "Warlord of Svargrond",
          "grade": 2,
          "isSecret": false
        }
      ],
      "deaths": [],
      "accountInformation": {
        "createdAt": "2004-05-22T04:35:43Z",
        "loyaltyTitle": "Warrior of Tibia",
        "position": null
      },
      "otherCharacters": [
        {
          "name": "Don Heron",
          "world": "Antica",
          "isMain": false,
          "isOnline": false,
          "isDeleted": false,
          "isRecentlyTraded": false,
          "position": null
        },
        {
          "name": "Eggplant Galarzaa",
          "world": "Secura",
          "isMain": false,
          "isOnline": false,
          "isDeleted": false,
          "isRecentlyTraded": false,
          "position": null
        },
        {
          "name": "Galarzaa",
          "world": "Calmera",
          "isMain": false,
          "isOnline": false,
          "isDeleted": false,
          "isRecentlyTraded": false,
          "position": null
        },
        {
          "name": "Galarzaa Deathbringer",
          "world": "Gladera",
          "isMain": false,
          "isOnline": false,
          "isDeleted": false,
          "isRecentlyTraded": false,
          "position": null
        },
        {
          "name": "Galarzaa Fidera",
          "world": "Gladera",
          "isMain": true,
          "isOnline": false,
          "isDeleted": false,
          "isRecentlyTraded": false,
          "position": null
        },
        {
          "name": "Galarzaa Redd",
          "world": "Gladera",
          "isMain": false,
          "isOnline": false,
          "isDeleted": false,
          "isRecentlyTraded": false,
          "position": null
        },
        {
          "name": "Galarzaa The Druid",
          "world": "Gladera",
          "isMain": false,
          "isOnline": false,
          "isDeleted": false,
          "isRecentlyTraded": false,
          "position": null
        },
        {
          "name": "Gallan Doente",
          "world": "Belobra",
          "isMain": false,
          "isOnline": false,
          "isDeleted": false,
          "isRecentlyTraded": false,
          "position": null
        },
        {
          "name": "Lord de los Druidas",
          "world": "Calmera",
          "isMain": false,
          "isOnline": false,
          "isDeleted": false,
          "isRecentlyTraded": false,
          "position": null
        },
        {
          "name": "Sir Galarzaa",
          "world": "Calmera",
          "isMain": false,
          "isOnline": false,
          "isDeleted": false,
          "isRecentlyTraded": false,
          "position": null
        },
        {
          "name": "Sir Heron",
          "world": "Zunera",
          "isMain": false,
          "isOnline": false,
          "isDeleted": false,
          "isRecentlyTraded": false,
          "position": null
        }
      ]
    }
    ```

TibiaKT also provides utilty functions to get the URL of a specific page, for example:

```kotlin
characterUrl("Galarzaa Fidera")
>> "https://www.tibia.com/community/?subtopic=characters&name=Galarzaa+Fidera"
```



