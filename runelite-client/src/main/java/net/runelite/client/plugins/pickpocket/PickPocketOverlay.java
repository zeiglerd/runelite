/**
 * @author Dustbin [pp drips]
 */

package net.runelite.client.plugins.pickpocket;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

import javax.inject.Inject;
import java.awt.*;

class PickPocketOverlay extends Overlay
{
	@Inject
	public PickPocketOverlay(final Client c, final PickPocketConfig config,
							 final PickPocketPlugin p)
	{
		this.client = c;
		this.config = config;
		this.plugin = p;

		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPriority(OverlayPriority.HIGHEST);
		setPosition(OverlayPosition.DYNAMIC);
	}

	@Override
	public Dimension render(final Graphics2D g)
	{
		UserInterfaceArcs.drawArcs(plugin, g);
		return null;
	}

	private final Client client;
	private final PickPocketPlugin plugin;
	private final PickPocketConfig config;
}
