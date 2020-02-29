/**
 * @author Dustbin [pp drips]
 */

package net.runelite.client.plugins.pickpocket;

import net.runelite.api.*;
import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.plugins.pickpocket.config.InterfaceOption;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.Arrays;
import java.util.Objects;

public interface PickPocketHelper
{
    static int getCoinPouchCount(final Client c)
    {
        final int[] COIN_POUCHES = {
                ItemID.COIN_POUCH,
                ItemID.COIN_POUCH_22522,
                ItemID.COIN_POUCH_22523,
                ItemID.COIN_POUCH_22524,
                ItemID.COIN_POUCH_22525,
                ItemID.COIN_POUCH_22526,
                ItemID.COIN_POUCH_22527,
                ItemID.COIN_POUCH_22528,
                ItemID.COIN_POUCH_22529,
                ItemID.COIN_POUCH_22530,
                ItemID.COIN_POUCH_22531,
                ItemID.COIN_POUCH_22532,
                ItemID.COIN_POUCH_22533,
                ItemID.COIN_POUCH_22534,
                ItemID.COIN_POUCH_22535,
                ItemID.COIN_POUCH_22536,
                ItemID.COIN_POUCH_22537,
                ItemID.COIN_POUCH_22538
        };
        int count = 0;
        for (Item item : Objects.requireNonNull(getInventory(c)))
        {
            if (item != null && item.getQuantity() > 0 && Arrays.stream(COIN_POUCHES).anyMatch(id -> id == item.getId()))
            {
                count += item.getQuantity();
            }
        }
        return count;
    }

    static int getBgDiameter(PickPocketPlugin p , UserInterfaceArcs[] arcs)
    {
        int idx = -1;
        if (p.getConfig().displayCoinPouchCountOnInterface())
        {
            idx++;
        }
        if (p.getConfig().displayDodgyNecklaceOnInterface())
        {
            idx++;
        }
        if (p.getConfig().displayGlovesOfSilenceOnInterface())
        {
            idx++;
        }
        if (p.getConfig().displayPlayerHitpointsOnInterface())
        {
            idx++;
        }
        if (p.getConfig().displayPlayerInventorySpaceOnInterface())
        {
            idx++;
        }

        return idx > -1 && idx < arcs.length ? arcs[idx].diameter + 10 : -1;
    }

    static Item[] getEquipment(final Client c)
    {
        final ItemContainer itemContainer = c.getItemContainer(InventoryID.EQUIPMENT);
        if (itemContainer == null)
        {
            return null;
        }
        return itemContainer.getItems();
    }

    static Item getEquippedGloves(final Client c)
    {
        final Item[] items = getEquipment(c);
        final int idx = EquipmentInventorySlot.GLOVES.getSlotIdx();
        if (items == null || idx >= items.length)
        {
            return null;
        }
        return items[idx];
    }

    static Item getEquippedNecklace(final Client c)
    {
        final Item[] items = getEquipment(c);
        final int idx = EquipmentInventorySlot.AMULET.getSlotIdx();
        if (items == null || idx >= items.length)
        {
            return null;
        }
        return items[idx];
    }

    static Item[] getInventory(final Client c)
    {
        final ItemContainer itemContainer = c.getItemContainer(InventoryID.INVENTORY);
        if (itemContainer == null)
        {
            return null;
        }
        return itemContainer.getItems();
    }

    static int getInventorySpaceCount(final Client c)
    {
        int count = 0;
        for (Item item : Objects.requireNonNull(getInventory(c)))
        {
            if (item != null && item.getQuantity() > 0)
            {
                count++;
            }
        }
        return count;
    }

    static java.awt.Point getUiLocation(PickPocketPlugin p, Graphics2D g)
    {
        return getUiLocation(p, g, p.getConfig().displayInterfaceOn());
    }

    static java.awt.Point getUiLocation(PickPocketPlugin p, Graphics2D g, InterfaceOption uiOption)
    {
        switch (uiOption)
        {
            case CENTER:
                return new java.awt.Point(p.getClient().getCanvasWidth() / 2,
                        p.getClient().getCanvasHeight() / 2);

            case MOUSE:
                net.runelite.api.Point mousePos = p.getClient().getMouseCanvasPosition();
                return new java.awt.Point(mousePos.getX(), mousePos.getY());

            case HULL:
                if (p.getLastTarget() == null)
                {
                    return null;
                }

                LocalPoint npcPoint = LocalPoint.fromWorld(p.getClient(), p.getLastTarget().getWorldLocation());
                if (npcPoint == null)
                {
                    return null;
                }

                // @todo we can probably do better than this (when getting draw coordinates)..?
//                Point npcLocation = Perspective.getCanvasTextLocation(p.getClient(), g,
//                        npcPoint, "nonsense", 150);

                Point npcLocation = Perspective.getCanvasTextLocation(p.getClient(), g,
                        npcPoint, "", 100);
//                Point npcLocation = Perspective.

                return new java.awt.Point(npcLocation.getX(), npcLocation.getY());

            default:
                return null;
        }
    }

    static boolean isWearingDodgyNecklace(final Client c)
    {
        final Item equippedAmulet = getEquippedNecklace(c);
        return equippedAmulet != null && equippedAmulet.getId() == ItemID.DODGY_NECKLACE;
    }

    static boolean isWearingGlovesOfSilence(final Client c)
    {
        final Item equippedGloves = getEquippedGloves(c);
        return equippedGloves != null && equippedGloves.getId() == ItemID.GLOVES_OF_SILENCE;
    }

    static Color Color(int r, int g, int b, float a)
    {
        return new Color((float)(r/255.), (float)(g/255.), (float)(b/255.), a);
    }

    static void drawArc(final PickPocketPlugin p, final Graphics2D g,
                        final UserInterfaceArcs[] arcs, final float start, final float extent,
                        final Color color, final java.awt.Point uiLocation,
                        final float strokeWidth, final boolean fill)
    {
        drawArc(p, g, PickPocketHelper.getBgDiameter(p, arcs), start, extent, color, uiLocation,
                strokeWidth, fill);
    }

    static void drawArc(final PickPocketPlugin p, final Graphics2D g, final int diameter,
                        final float start, final float extent, final Color color,
                        final java.awt.Point uiLocation, final float strokeWidth,
                        final boolean fill)
    {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final Arc2D arc = new Arc2D.Float(Arc2D.OPEN);
        arc.setAngleExtent(extent);
        arc.setAngleStart(start);
        arc.setFrameFromCenter(uiLocation.x, uiLocation.y,
                uiLocation.x + diameter, uiLocation.y + diameter);

        // Draw the inside of the arc
        g.setColor(color);
        if (fill)
        {
            g.fill(arc);
        }
        g.draw(arc);
        g.setStroke(new BasicStroke(strokeWidth));

        // Draw the outlines of the arc
//        g.setStroke(new BasicStroke(1f));
//        g.setColor(Color.BLACK);
//        g.drawOval(uiLocation.x - diameter / 2, uiLocation.y - diameter / 2, diameter, diameter);
    }
}
