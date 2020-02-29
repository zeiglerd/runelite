/**
 * @author Dustbin [pp drips]
 */

package net.runelite.client.plugins.pickpocket.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.event.KeyEvent;

@Getter
@RequiredArgsConstructor
public enum HotKeyOption
{
    NONE("None", -1),
    CTRL("Control+Click", KeyEvent.VK_CONTROL),
    SHIFT("Shift+Click", KeyEvent.VK_SHIFT);

    private final String name;
    private final int value;

    @Override
    public final String toString()
    {
        return name;
    }
}
