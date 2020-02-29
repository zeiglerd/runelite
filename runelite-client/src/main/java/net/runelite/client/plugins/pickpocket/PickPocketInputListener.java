/**
 * @author Dustbin [pp drips]
 */

package net.runelite.client.plugins.pickpocket;

import net.runelite.client.input.KeyListener;
import net.runelite.client.input.MouseAdapter;

import javax.inject.Inject;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PickPocketInputListener extends MouseAdapter implements KeyListener
{
    @Inject
    private PickPocketPlugin p = null;

    @Override
    public final void keyPressed(final KeyEvent event)
    {
        final int enableWalkHereOn = p.getConfig().enableWalkHereOn().getValue();

        if (enableWalkHereOn > -1 && enableWalkHereOn == event.getKeyCode())
        {
            p.setEnableWalkHereOnPressed(true);
        }
    }

    @Override
    public final void keyReleased(final KeyEvent event)
    {
        p.setEnableWalkHereOnPressed(false);
    }

    @Override
    public final void keyTyped(final KeyEvent event)
    {

    }

    @Override
    public final MouseEvent mousePressed(final MouseEvent event)
    {
        return event;
    }
}
