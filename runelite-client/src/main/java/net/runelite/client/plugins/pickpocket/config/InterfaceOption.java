/**
 * @author Dustbin [pp drips]
 */

package net.runelite.client.plugins.pickpocket.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InterfaceOption
{
    HULL("On NPC hull", "hull"),
    MOUSE("Mouse X and Y", "mouse"),
    CENTER("Center screen", "center"),
    NONE("Nowhere", null);

    private final String name;
    private final String value;

    @Override
    public final String toString()
    {
        return name;
    }
}
