/**
 * @author Dustbin [pp drips]
 */

package net.runelite.client.plugins.pickpocket.message;

import net.runelite.client.plugins.pickpocket.PickPocketPlugin;

import java.util.regex.Matcher;

interface MessageMapperHelper
{
    static boolean setDodgyNecklaceChargeDynamically(final PickPocketPlugin p, final Matcher matcher)
    {
        p.getConfig().dodgyNecklaceCharge(Integer.parseInt(matcher.group(1)));
        return true;
    }
}
