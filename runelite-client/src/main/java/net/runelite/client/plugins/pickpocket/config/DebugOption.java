/**
 * @author Dustbin [pp drips]
 */

package net.runelite.client.plugins.pickpocket.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DebugOption
{
    ALL("All (-vvv)"),
    DESTROY_ITEM("Destroy item"),
    DISPLAY_INTERFACE("Display UI"),
    DISABLE_WALK_HERE("Disable \"Walk here\""),
    FORCE(""),
    GAME_STATE("Game state"),
    ITEMS("Items"),
    ITEM_SLOT_CHANGED("Item slot changed"),
    NONE("None");

    private final String name;

    @Override
    public final String toString()
    {
        return (name != null && name.length() > 0) ? name : null;
    }
}
