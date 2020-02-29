/**
 * @author Dustbin [pp drips]
 */

package net.runelite.client.plugins.pickpocket;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Units;
import net.runelite.client.plugins.pickpocket.config.DebugOption;
import net.runelite.client.plugins.pickpocket.config.HotKeyOption;
import net.runelite.client.plugins.pickpocket.config.InterfaceOption;

@ConfigGroup("pickpocket")
public interface PickPocketConfig extends Config
{
    /**
     * --------------------------------------------------
     * Core configuration
     * --------------------------------------------------
     */

    @ConfigItem(
            position = 1,
            keyName = "disableWalkHere",
            name = "Disable Walk",
            description = "Prevents the Player from using, \"Walk here\" unless specified hot key is pressed."
    )
    default boolean disableWalkHere()
    {
        return true;
    }

    @ConfigItem(
            position = 2,
            keyName = "enableWalkHereOn",
            name = "Enable Walk on hot key",
            description = "Choose which hot key permits the Player to, \"Walk here\" while pressed."
    )
    default HotKeyOption enableWalkHereOn()
    {
        return HotKeyOption.SHIFT;
    }

    @ConfigItem(
            position = 3,
            keyName = "disablePickpocketIfPlayerCanDie",
            name = "Save Player",
            description = "Prevents the Player from pickpocketing if they have less than 4 Hitpoints."
    )
    default boolean disablePickpocketIfPlayerCanDie()
    {
        return true;
    }

    @ConfigItem(
            position = 4,
            keyName = "disablePickpocketIfGlovesOfSilenceCanFallApart",
            name = "Save Gloves of silence",
            description = "Prevents the Player from pickpocketing if their Gloves of silence are about to fall apart."
    )
    default boolean disablePickpocketIfGlovesOfSilenceCanFallApart()
    {
        return true;
    }


    /**
     * --------------------------------------------------
     * Interface configuration
     * --------------------------------------------------
     */

    @ConfigItem(
            position = 5,
            keyName = "displayInterfaceOn",
            name = "Display UI",
            description = "Choose where to display the pickpocketing UI."
    )
    default InterfaceOption displayInterfaceOn()
    {
        return InterfaceOption.HULL;
    }

    @ConfigItem(
            position = 6,
            keyName = "hideInterfaceAfter",
            name = "Hide UI after",
            description = "Choose how long before automatically hiding the pickpocketing UI... Set this to 0 if you never want to automatically hide the pickpocketing UI."
    )
    @Units(Units.MILLISECONDS)
    default int hideInterfaceAfter()
    {
        return 25000;
    }

    @ConfigItem(
            position = 7,
            keyName = "backgroundInterfaceOpacity",
            name = "Background interface opacity",
            description = "Choose the background opacity for the pickpocketing UI; Choose 0.0 through 1.0"
    )
    default String backgroundInterfaceOpacity()
    {
        return "0.4";
    }

    @ConfigItem(
            position = 8,
            keyName = "foregroundInterfaceOpacity",
            name = "Foreground interface opacity",
            description = "Choose the foreground opacity for the pickpocketing UI; Choose 0.0 through 1.0"
    )
    default String foregroundInterfaceOpacity()
    {
        return "0.8";
    }

    @ConfigItem(
            position = 9,
            keyName = "displayCoinPouchCountOnInterface",
            name = "Show Coin pouch count",
            description = "Choose if Coin pouch count is displayed in the pickpocketing UI."
    )
    default boolean displayCoinPouchCountOnInterface()
    {
        return true;
    }

    @ConfigItem(
            position = 10,
            keyName = "coinPouchCountColorOnInterface",
            name = "Coin pouch count color",
            description = "Choose the color used to depict Coin pouch count in the pickpocketing UI; Choose any hex color code."
    )
    default String coinPouchCountColorOnInterface()
    {
        return "e3c032";
    }

    @ConfigItem(
            position = 11,
            keyName = "displayDodgyNecklaceOnInterface",
            name = "Show Dodgy necklace charge",
            description = "Choose if Dodgy necklace charge is displayed in the pickpocketing UI."
    )
    default boolean displayDodgyNecklaceOnInterface()
    {
        return true;
    }

    @ConfigItem(
            position = 12,
            keyName = "dodgyNecklaceColorOnInterface",
            name = "Dodgy necklace charge color",
            description = "Choose the color used to depict Dodgy necklace charge in the pickpocketing UI; Choose any hex color code."
    )
    default String dodgyNecklaceColorOnInterface()
    {
        return "8dc265";
    }

    @ConfigItem(
            position = 13,
            keyName = "displayGlovesOfSilenceOnInterface",
            name = "Show Gloves of silence charge",
            description = "Choose if Gloves of silence charge is displayed in the pickpocketing UI."
    )
    default boolean displayGlovesOfSilenceOnInterface()
    {
        return true;
    }

    @ConfigItem(
            position = 14,
            keyName = "glovesOfSilenceColorOnInterface",
            name = "Gloves of silence charge color",
            description = "Choose the color used to depict Gloves of silence charge in the pickpocketing UI; Choose any hex color code."
    )
    default String glovesOfSilenceColorOnInterface()
    {
        return "282b28";
    }

    @ConfigItem(
            position = 15,
            keyName = "displayPlayerHitpointsOnInterface",
            name = "Show Player HP",
            description = "Choose if Player Hitpoints is displayed in the pickpocketing UI."
    )
    default boolean displayPlayerHitpointsOnInterface()
    {
        return true;
    }

    @ConfigItem(
            position = 16,
            keyName = "playerHitpointsColorOnInterface",
            name = "Player Hitpoints color",
            description = "Choose the color used to depict Player Hitpoints in the pickpocketing UI; Choose any hex color code."
    )
    default String playerHitpointsColorOnInterface()
    {
        return "1fd916";
    }

    @ConfigItem(
            position = 17,
            keyName = "displayPlayerInventorySpaceOnInterface",
            name = "Show Inventory space",
            description = "Choose if Inventory space is displayed in the pickpocketing UI."
    )
    default boolean displayPlayerInventorySpaceOnInterface()
    {
        return true;
    }

    @ConfigItem(
            position = 18,
            keyName = "playerInventorySpaceColorOnInterface",
            name = "Inventory space color",
            description = "Choose the color used to depict Inventory space in the pickpocketing UI; Choose any hex color code."
    )
    default String playerInventorySpaceColorOnInterface()
    {
        return "664f2f";
    }


    /**
     * --------------------------------------------------
     * Debugging configuration
     * --------------------------------------------------
     */

    @ConfigItem(
            position = 19,
            keyName = "debugLevel",
            name = "Debug level",
            description = "Choose the debug level for plugin."
    )
    default DebugOption debugLevel()
    {
        return DebugOption.NONE;
    }


    /**
     * --------------------------------------------------
     * Plugin store
     * --------------------------------------------------
     */

    @ConfigItem(keyName = "dodgyNecklaceCharge", name = "", description = "", hidden = true)
    default int dodgyNecklaceCharge()
    {
        return -1;
    }

    @ConfigItem(keyName = "dodgyNecklaceCharge", name = "", description = "")
    void dodgyNecklaceCharge(final int charge);

    @ConfigItem(keyName = "glovesOfSilenceCharge", name = "", description = "", hidden = true)
    default String glovesOfSilenceCharge()
    {
        return null;
    }

    @ConfigItem(keyName = "glovesOfSilenceCharge", name = "", description = "")
    void glovesOfSilenceCharge(final String charge);
}
