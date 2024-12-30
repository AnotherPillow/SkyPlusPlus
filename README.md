# Sky++

Yet another skyblock.net mod for 1.19.2

[![img](https://img.shields.io/github/release/anotherpillow/SkyPlusPlus.svg)](https://github.com/AnotherPillow/SkyPlusPlus/releases/)
[![img](https://img.shields.io/github/downloads/anotherpillow/SkyPlusPlus/total.svg)](https://github.com/AnotherPillow/SkyPlusPlus/releases/)

## Installation

- Download latest release from [here](https://modrinth.com/mod/sky++/versions)
- Download the following prerequisites:
  - [Fabric Loader](https://fabricmc.net/use/)
  - [Fabric API](https://modrinth.com/mod/fabric-api)
  - [Mod Menu](https://modrinth.com/mod/modmenu)
- Put all the downloaded files in your `mods` folder

## Features

| Feature                        | Description                                                                                                                                                                                                                                                                                                                                                                                |
|--------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Wandering Trader Notifications | Shows a countdown until the next wandering trader spawns, and where it is at spawn. Also optional to enable a title upon trader appearance.                                                                                                                                                                                                                                                |
| Chat Filters                   | Filters out messages from the chat. Filters include: <br> Island welcome messages<br>Visiting Island messages<br>Visiting Island titles<br>`[Skyblock]` messages<br>Staff going AFK<br>New user messages<br>Void counter messages<br>Raffle messages<br>Advancement messages<br>Broadcast messages<br>Lucky Crate messages<br>Unread mail notifications<br>Island needs expanding messages |
| Auto Responder                 | Automatically responds to messages with a configurable message.                                                                                                                                                                                                                                                                                                                            |
| Chat Prefix/Suffix             | Adds a configurable prefix and suffix to your chat messages.                                                                                                                                                                                                                                                                                                                               |
| Better Changebiome             | Changes icons in the `/changebiome` UI to be small versions of the biomes.                                                                                                                                                                                                                                                                                                                 |
| Better Crate Keys              | Makes crate keys colourful.                                                                                                                                                                                                                                                                                                                                                                |
| ExtraTab                       | Expands the tab menu, allowing much more columns.                                                                                                                                                                                                                                                                                                                                          |
| Prevent Head Dropper           | Prevents dropping of all heads. (Only through the drop button, not inventory)                                                                                                                                                                                                                                                                                                              |
| Discord RPC                    | Custom Discord Rich Presence                                                                                                                                                                                                                                                                                                                                                               |
| AutoRaffle                     | Automatically buys 5 raffle tickets each round                                                                                                                                                                                                                                                                                                                                             |
| Automatic Lowercase Commands   | Automatically converts commands to be lowercase, to bypass the server's case sensitivity.                                                                                                                                                                                                                                                                                                  |
| Show Empty Shops               | Makes empty shops in `/shops find ` barrier blocks.                                                                                                                                                                                                                                                                                                                                        |
| Prevent Grass Placing          | Prevents you from placing grass blocks.                                                                                                                                                                                                                                                                                                                                                    |
| Anti PC                        | Known more commonly as AntiBookBan. Prevents large items from kicking you from the server. This is often used to troll. Exists only because of PC                                                                                                                                                                                                                                          |
| Dynamic Scoreboard Title       | Makes the scoreboard title change depending on connected subserver.                                                                                                                                                                                                                                                                                                                        |
| Auto Advertisements            | Sends a message in chat every specified delay. Will not allow you to break server advertisement delay rules. Messages sent using this have a hidden colour code added at the end for future advertising blocks.                                                                                                                                                                            |
| Join Commands                  | Automatically send commands/chat messages when you join the server.                                                                                                                                                                                                                                                                                                                        |
| Chat Rank Removal              | Hides the [Rank] part of global chat messages                                                                                                                                                                                                                                                                                                                                              |

## Commands

| Command        | Description                                                                    |
|----------------|--------------------------------------------------------------------------------|
| `/sconvert`    | Converts an amount of items to single chests, double chests or stacks.         |
| `/smarttp`     | A better `/tpahere`, automatically unlocks and relocks your island.            |
| `/skyplusplus` | Opens the config menu (same menu you can get through modmenu.                  |
| `/runafter`    | Runs/sends text after specified (ms) timeout. Example: `/runafter 1000 /spawn` |

## Keybinds

| Keybind          | Default bind | Description                                                       |
|------------------|--------------|-------------------------------------------------------------------|
| Copy Hovered NBT | `c`          | Sends the hovered item's NBT to chat. Only applies in inventories |
 

## Credits

- [sbutils](https://github.com/hashalite/sbutils-public) for helping me figure out YACL.
- [Meteor Client](https://github.com/MeteorDevelopment/meteor-client/blob/master/src/main/java/meteordevelopment/meteorclient/systems/modules/misc/DiscordPresence.java) and their [discord-ipc library](https://github.com/MeteorDevelopment/discord-ipc) for Discord RPC.
