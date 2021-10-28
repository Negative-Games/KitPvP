# KitPvP-Framework
This repository contains a framework built for KitPvP plugins. The main purpose of this project is if I wanted to create
a new KitPvP project of some sorts, I can use this framework as a starting base for the plugin I plan to make.

[![CodeFactor](https://www.codefactor.io/repository/github/negativekb/kitpvp-framework/badge)](https://www.codefactor.io/repository/github/negativekb/kitpvp-framework) 

## Feature List

This framework contains the following features

### Easy to use API

The internal API is crucial for the plugin to work. It is how most classes access features such as Profiles.

You can access the API by doing the following:

```JAVA
KitPvPAPI api = KitPvPAPI.getInstance();

// Example
// Get the manager for Profiles
ProfileManager profileManager = api.getProfileManager();
```

### Profiles

Profiles is a custom stats-tracking object where you can store data and will be printed to JSON. This uses
the [Gson Library](https://github.com/google/gson).

Each profile contains the following stats:

* Unique ID (The UUID of the Player/Owner of the Profile)
* Kills (an Integer which represents how many kills you have)
* Deaths (an Integer which represents how many deaths you have)
* KillStreak (an Integer which represents how many kills you have had since your last death)
* Best KillStreak (an Integer which represents your best KillStreak of all time)
* Assists (an Integer which represents how many players you have assisted in killing)
* Coins (a Long which represents how much currency you possess)
* Cosmetics Status (an Object which contains 3 enums of your Kill Effect, Kill Message, and Kill Sound)
* Current Kit (an Enum which represents your current kit you have selected)

### Region

Region is a region system which can be used internally without the need of third party region plugins such as
WorldGuard. You can use it internally to determine custom conditions for any piece of code you may make.

### Cosmetics
Cosmetics are a non pay-to-win way for servers to monetize their KitPvP gamemode. This framework contains 3 types of Cosmetics.

#### Kill Effects
When you kill a player with a Kill Effect enabled, it will run the corresponding code. It could spawn particles, mobs, or anything to your heart's desire!

#### Kill Messages
When you kill a player with a Kill Message enabled, it will send both the victim and killer the corresponding message. The message is defined inside of the code.

#### Kill Sounds
When you kill a player it will send the killer the corresponding sound. The sound is defined inside of the code.

### Ability Items
Ability Items are custom items which when its corresponding condition is met, such as Left-Clicking, Right-Clicking, or damaging a player, it will execute its corresponding code.

### Easy Command API
The Command API allows you to create commands easier than using regular CommandExecutor. The API is provided from [DeltaAPI](https://github.com/Delta-Development/DeltaAPI).

Please read on how to use this API [here](https://wiki.deltapvp.club/deltaapi/the-basics/commands)!

### Easy GUI API
The GUI API allows you to create interactive menus without the need of creating listeners all over the place. The API is provided from [DeltaAPI](https://github.com/Delta-Development/DeltaAPI).

Please read on how to use this API [here](https://wiki.deltapvp.club/deltaapi/the-basics/gui)!
## Notice

This Mark Down will be expanded upon at a later date. This currently does not cover all the features of the framework.
