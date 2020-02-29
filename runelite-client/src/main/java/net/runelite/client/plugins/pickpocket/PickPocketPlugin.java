/**
 * @author Dustbin [pp drips]
 */

package net.runelite.client.plugins.pickpocket;

import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.input.MouseManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.pickpocket.message.MessageMapper;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@PluginDescriptor(
    name = "Pick Pocket",
    description = "A [helpful] plugin to use while pickpocketing NPCs.",
    tags = { "pickpocketing", "thieving" },
    enabledByDefault = false
)
public class PickPocketPlugin extends Plugin
{
    @Override
    protected void startUp() throws Exception
    {
        if (overlayManager != null && overlay != null)
        {
            overlayManager.add(overlay);
        }
    }

    @Override
    protected void shutDown() throws Exception
    {
        if (inputListener != null) {
            if (keyManager != null)
            {
                keyManager.unregisterKeyListener(inputListener);
            }

            if (mouseManager != null)
            {
                mouseManager.unregisterMouseListener(inputListener);
            }
        }

        if (overlayManager != null && overlay != null)
        {
            overlayManager.remove(overlay);
        }

        lastDestroyWidget = -1;
    }

    @Provides
    PickPocketConfig getConfig(final ConfigManager configManager)
    {
        return configManager.getConfig(PickPocketConfig.class);
    }

    @Subscribe
    private void onChatMessage(final ChatMessage chatMessage)
    {
        if (chatMessage.getType() == ChatMessageType.GAMEMESSAGE
                || chatMessage.getType() == ChatMessageType.SPAM)
        {
            for (MessageMapper map : MessageMapper.values())
            {
                if (map.callMethodIfFound(this, chatMessage.getMessage()))
                {
                    return;
                }
            }
        }
    }

    @Subscribe
    private void onGameStateChanged(final GameStateChanged gameStateChanged)
    {
        if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
        {
            keyManager.registerKeyListener(inputListener);
            mouseManager.registerMouseListener(inputListener);
        }
    }

    @Subscribe
    private void onGameTick(GameTick gameTick)
    {
        int hideInterfaceAfter = config.hideInterfaceAfter();
        if (pickpocketing && hideInterfaceAfter > 0 && lastPickpocket > -1
                && System.currentTimeMillis() > (hideInterfaceAfter + lastPickpocket))
        {
            setPickpocketing(false);
        }
    }

    @Subscribe
    private void onInteractingChanged(InteractingChanged interactingChanged)
    {
        if (interactingChanged.getSource() == client.getLocalPlayer())
        {
            Actor opponent = interactingChanged.getTarget();
            if (opponent == null)
            {
                return;
            }
            lastTarget = opponent;
        }
    }

    @Subscribe
    private void onMenuOptionClicked(final MenuOptionClicked menuOptionClicked)
    {
        switch (menuOptionClicked.getMenuOption())
        {
            case "Pickpocket":
                final int DANGER_HP = 4; // @todo configurable soon
                if (getClient().getBoostedSkillLevel(Skill.HITPOINTS) < DANGER_HP
                        && config.disablePickpocketIfPlayerCanDie())
                {
                    menuOptionClicked.consume();
                    return;
                }

                if (PickPocketHelper.isWearingGlovesOfSilence(getClient())
                        && config.disablePickpocketIfGlovesOfSilenceCanFallApart()
                        && config.glovesOfSilenceCharge().equals("=1"))
                {
                    menuOptionClicked.consume();
                    return;
                }

                setPickpocketing(true);
                return;

            case "Walk here":
                if (config.disableWalkHere() && !enableWalkHereOnPressed)
                {
                    menuOptionClicked.consume();
                }
                return;

            default:
                // Do nothing...
        }
    }

    protected void setPickpocketing(final boolean pickpocketing)
    {
        this.pickpocketing = pickpocketing;
        lastPickpocket = pickpocketing ? System.currentTimeMillis() : -1;
        uiEnabled = pickpocketing;
//        lastTarget = pickpocketing
//                ? Objects.requireNonNull(client.getLocalPlayer()).getInteracting()
//                : null;
    }

    @Getter(AccessLevel.PACKAGE)
    @Inject
    private Client client = null;

    @Getter(AccessLevel.PACKAGE)
    @Inject
    private ItemManager itemManager = null;

    @Getter(AccessLevel.PACKAGE)
    @Inject
    private KeyManager keyManager = null;

    @Getter(AccessLevel.PACKAGE)
    @Inject
    private MouseManager mouseManager = null;

    @Getter(AccessLevel.PACKAGE)
    @Inject
    private OverlayManager overlayManager = null;

    @Getter(AccessLevel.PUBLIC)
    @Inject
    private PickPocketConfig config = null;

    @Getter(AccessLevel.PACKAGE)
    @Inject
    private PickPocketInputListener inputListener = null;

    @Getter(AccessLevel.PACKAGE)
    @Inject
    private PickPocketOverlay overlay = null;

    @Getter(AccessLevel.PACKAGE)
    private int lastDestroyWidget = -1;

    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private boolean enableWalkHereOnPressed = false;

    @Getter(AccessLevel.PACKAGE)
    private boolean pickpocketing = false;

    @Getter(AccessLevel.PACKAGE)
    private long lastPickpocket = -1;

    @Getter(AccessLevel.PACKAGE)
    private boolean uiEnabled = false;

    @Getter(AccessLevel.PACKAGE)
    private Actor lastTarget = null;
}
