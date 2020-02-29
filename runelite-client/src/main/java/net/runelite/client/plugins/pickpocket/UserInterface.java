/**
 * @author Dustbin [pp drips]
 */

package net.runelite.client.plugins.pickpocket;

import net.runelite.api.Skill;
import net.runelite.client.plugins.pickpocket.message.MessageMapperConfig;

import java.awt.*;
import java.util.function.Function;

import static net.runelite.client.plugins.pickpocket.message.MessageMapperConfig.DODGY_NECKLACE_MAX_CHARGE;
import static net.runelite.client.plugins.pickpocket.message.MessageMapperConfig.GLOVES_OF_SILENCE_MAX_CHARGE;

public enum UserInterface
{
    COIN_POUCH_COUNT("Coin pouch", (final PickPocketPlugin p) -> {
        final Color decode = Color.decode("#" + p.getConfig().coinPouchCountColorOnInterface());
        return new Color(decode.getRed(), decode.getGreen(), decode.getBlue(),
                (int) (Float.parseFloat(p.getConfig().foregroundInterfaceOpacity()) * 255));
    }, (final PickPocketPlugin p) -> {
        if (p.getConfig().displayCoinPouchCountOnInterface())
        {
            return 360f * (PickPocketHelper.getCoinPouchCount(p.getClient()) / 28f);
        }
        return -1f;
    }),

    DODGY_NECKLACE_CHARGE("Dodgy necklace", (final PickPocketPlugin p) -> {
        final Color decode = Color.decode("#" + p.getConfig().dodgyNecklaceColorOnInterface());
        return new Color(decode.getRed(), decode.getGreen(), decode.getBlue(),
                (int) (Float.parseFloat(p.getConfig().foregroundInterfaceOpacity()) * 255));
    }, (final PickPocketPlugin p) -> {
        if (p.getConfig().displayDodgyNecklaceOnInterface())
        {
            return -360f * (p.getConfig().dodgyNecklaceCharge() / 10f);
        }
        return -1f;
    }),

    GLOVES_OF_SILENCE_CHARGE("Gloves of silence", (final PickPocketPlugin p) -> {
        final Color decode = Color.decode("#" + p.getConfig().glovesOfSilenceColorOnInterface());
        return new Color(decode.getRed(), decode.getGreen(), decode.getBlue(),
                (int) (Float.parseFloat(p.getConfig().foregroundInterfaceOpacity()) * 255));
    }, (final PickPocketPlugin p) -> {
        if (p.getConfig().displayGlovesOfSilenceOnInterface())
        {
            return -360f * (Float.parseFloat(p.getConfig().glovesOfSilenceCharge()
                    .replaceAll("\\D+","")) / 62f);
        }
        return -1f;
    }),

    PLAYER_HITPOINTS("Hitpoints", (final PickPocketPlugin p) -> {
        final Color decode = Color.decode("#" + p.getConfig().playerHitpointsColorOnInterface());
        return new Color(decode.getRed(), decode.getGreen(), decode.getBlue(),
                (int) (Float.parseFloat(p.getConfig().foregroundInterfaceOpacity()) * 255));
    }, (final PickPocketPlugin p) -> {
        if (p.getConfig().displayPlayerHitpointsOnInterface())
        {
            return -360f * (p.getClient().getBoostedSkillLevel(Skill.HITPOINTS) / 99f);
        }
        return -1f;
    }),

    PLAYER_INVENTORY_SPACE("Inventory", (final PickPocketPlugin p) -> {
        final Color decode = Color.decode("#" + p.getConfig().playerInventorySpaceColorOnInterface());
        return new Color(decode.getRed(), decode.getGreen(), decode.getBlue(),
                (int) (Float.parseFloat(p.getConfig().foregroundInterfaceOpacity()) * 255));
    }, (final PickPocketPlugin p) -> {
        if (p.getConfig().displayPlayerInventorySpaceOnInterface())
        {
            return 360f * (PickPocketHelper.getInventorySpaceCount(p.getClient()) / 28f);
        }
        return -1f;
    });

    String name = null;
    Function<PickPocketPlugin, Color> color = null;
    Function<PickPocketPlugin, Float> extent = null;

    UserInterface(final String name,
                  final Function<PickPocketPlugin, Color> color,
                  final Function<PickPocketPlugin, Float> extent)
    {
        this.name = name;
        this.color = color;
        this.extent = extent;
    }

    static UserInterface[] getOrder()
    {
        return new UserInterface[] {
                PLAYER_HITPOINTS,
                COIN_POUCH_COUNT,
                DODGY_NECKLACE_CHARGE,
                GLOVES_OF_SILENCE_CHARGE,
                PLAYER_INVENTORY_SPACE
        };
    }
}
