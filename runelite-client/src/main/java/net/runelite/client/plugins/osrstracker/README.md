# plugin-java-osrstracker


## How-to: Install
1. Follow guide: "Building with IntelliJ IDEA" (located here: https://github.com/runelite/runelite/wiki/Building-with-IntelliJ-IDEA), until you have built and run RuneLite, using IntelliJ.
    * **Note:** When IntelliJ prompts for a repository to clone, use: `https://github.com/zeiglerd/runelite`
2. Using your local terminal (I will be using [Git Bash](https://git-scm.com/downloads))...
    1. Change directory to `zeiglerd/runelite` repository (created during step 1).
        * For me this was, **$** `cd '/c/Users/Dustin/Desktop/runelite'`
    2. Checkout branch `plugin-osrstracker` in `zeiglerd/runelite` repository.
        * **$** `git checkout plugin-osrstracker`
    3. Change directory to `osrstracker` plugin directory (created in step 2.2).
        * For me this was, **$** `cd '/c/Users/Dustin/Desktop/runelite/runelite-client/src/main/java/net/runelite/client/plugins/osrstracker'`
    4. Clone plugin source (available here: https://github.com/zeiglerd/plugin-java-osrstracker/commits/master) direcctly into `osrstracker` plugin directory.
        * **$** `git clone git@github.com:zeiglerd/plugin-java-osrstracker.git .`
        * **NOTE:** The plugin source files must be copied directly into `osrstracker` plugin directory, so that the structure vaugley matches:
            * runelite/
                * client/
                    * plugins/
                        * osrstracker/
                            * ...
                            * OsrsTrackerPlugin.java
                            * README.md
                            * ...
4. Using IntelliJ, run RuneLite (like in step 1), and the OSRS Tracker plugin will be running!

**That's it!**

**Note:** You will have to continue running RuneLite, with IntelliJ; or, command line, in order to continue using the OSRS Tracker plugin.


## How-to: Disable
Just like any other RuneLite plugin, OSRS Tracker can be disabled -- and subsequently enabled -- in *Configuration*.


## How-to: Remove
Simply delete the `osrstracker` package you created during step 2.x of [How-to: Install](#how-to-install).
