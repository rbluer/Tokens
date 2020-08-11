[![Total alerts](https://img.shields.io/lgtm/alerts/g/TheCoolestPaul/Tokens.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/TheCoolestPaul/Tokens/alerts/)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/TheCoolestPaul/Tokens.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/TheCoolestPaul/Tokens/context:java)

*Jit-CI:* [![](https://jitci.com/gh/TheCoolestPaul/Tokens/svg)](https://jitci.com/gh/TheCoolestPaul/Tokens)

*Jenkins Builds:* [![Build Status](https://ci.codemc.io/job/TheCoolestPaul/job/Tokens/badge/icon)](https://ci.codemc.io/job/TheCoolestPaul/job/Tokens/)

**What is the Tokens plugin?**  

Tokens was initially developed for use in my private server, the idea was to give players a special currency "Tokens" that they can use to allocate to mcMMO Levels, faction power, or convert straight into money. I plan on adding support for many more plugins as the request come in, I will be keeping this plugin up-to-date.​

**What plugins are supported?**  

-   **[Factions UUID](https://www.spigotmc.org/resources/factionsuuid.1035/)**
-   **[mcMMO Classic](https://www.spigotmc.org/resources/official-mcmmo-classic.2445/)**
-   **[mcMMO Revived](https://www.spigotmc.org/resources/official-mcmmo-original-author-returns.64348/)**
-   **[CombatLogX](https://www.spigotmc.org/resources/combatlogx.31689/)**
-   **[PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)**
-   **[Any Vault Economy](https://www.spigotmc.org/resources/vault.34315/)**

Want a plugin on this list? Let me know  [here](https://github.com/TheCoolestPaul/Tokens/issues), make sure to include a link to the plugin, what a token should be spent on, and any information you think I'll need to add it.​

**How do I Install it?**  

1.  Download from Spigot
2.  Drop into your plugins folder
3.  Run the server
4.  Edit config.yml inside of the plugins/Tokens folder
5.  /tokens reload
6.  Profit??

**How do I add keys?**

Checkout the `keys.yml` file, there are three different example keys that you can look at.

Once you edit `keys.yml` you can use /tokens reload to apply your changes in game,

**What are the commands?**  

-   /tokens (Used to show the player how many tokens they have)
-   /tokens give [player name] [token amount] (Used for players to give tokens to one another)
-   /tokens add [player name] [token amount] (Used to add a number of tokens to a player's tokens)
-   /tokens remove [player name] [token amount] (Used to remove a number of tokens from a player's tokens)
-   /tokens set [player name] [token amount] (Used to set a player's tokens to a number)
-   /tokens reload (Used to reload the config into the plugin)
-   /redeem (Command used to show help for redeeming tokens)
-   /redeem <key> (Command used to redeem a key)

**What are the permissions?**  

-   tokens.use (Recommended for all users /tokens)
-   tokens.give (Recommended for all users /tokens give)
-   tokens.add (Recommended for admins /tokens add)
-   tokens.remove (Recommended for admins/tokens remove)
-   tokens.set (Recommended for admins /tokens set)
-   tokens.reload (Recommended for admins /tokens reload)

**What if I need support?**  
  
If it's a bug report or a plugin support request I use Github  [here](https://github.com/TheCoolestPaul/Tokens/issues)
If there's anything else I use the "Discussion" page on SpigotMC [here](https://www.spigotmc.org/threads/tokens-multi-plugin-support-mysql-support.399524/)
