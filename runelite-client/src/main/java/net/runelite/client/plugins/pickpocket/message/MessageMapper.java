/**
 * @author Dustbin [pp drips]
 */

package net.runelite.client.plugins.pickpocket.message;

import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.client.plugins.pickpocket.PickPocketPlugin;

import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter(AccessLevel.PUBLIC)
public enum MessageMapper
{
    /**
     * Dodgy necklace
     */

    DODGY_BREAK_PATTERN(Pattern.compile(
            "Your dodgy necklace protects you\\..*It then crumbles to dust\\."
    ), (final PickPocketPlugin p, final Matcher matcher) -> {
        p.getConfig().dodgyNecklaceCharge(MessageMapperConfig.DODGY_NECKLACE_MAX_CHARGE);
        return true;
    }),

    DODGY_CHECK_PATTERN(Pattern.compile(
            "Your dodgy necklace has (\\d+) charges? left\\."
    ), MessageMapperHelper::setDodgyNecklaceChargeDynamically),

    DODGY_PROTECT_PATTERN(Pattern.compile(
            "Your dodgy necklace protects you\\..*It has (\\d+) charges? left\\."
    ), MessageMapperHelper::setDodgyNecklaceChargeDynamically),


    /**
     * Gloves of silence
     */

    NEW(Pattern.compile(
            "Your gloves are new\\."
    ), (final PickPocketPlugin p, final Matcher matcher) -> {
        p.getConfig().glovesOfSilenceCharge("=62");
        return true;
    }),

    GOOD_CONDITION(Pattern.compile(
            "Your gloves are in good condition\\."
    ), (final PickPocketPlugin p, final Matcher matcher) -> {
        p.getConfig().glovesOfSilenceCharge("<62");
        return true;
    }),

    QUITE_SHABBY(Pattern.compile(
            "Your gloves are starting to look quite shabby\\."
    ), (final PickPocketPlugin p, final Matcher matcher) -> {
        p.getConfig().glovesOfSilenceCharge("<34");
        return true;
    }),

    STARTING_TO_NEED_REPAIR(Pattern.compile(
            "Your gloves are starting to need repair\\."
    ), (final PickPocketPlugin p, final Matcher matcher) -> {
        p.getConfig().glovesOfSilenceCharge("<20");
        return true;
    }),

    NEED_REPAIR(Pattern.compile(
            "Your gloves are in need of repair!"
    ), (final PickPocketPlugin p, final Matcher matcher) -> {
        p.getConfig().glovesOfSilenceCharge("<10");
        return true;
    }),

    ABOUT_TO_FALL_APART(Pattern.compile(
            "Your gloves are about to fall apart!"
    ), (final PickPocketPlugin p, final Matcher matcher) -> {
        p.getConfig().glovesOfSilenceCharge("=1");
        return true;
    }),

    GOING_TO_FALL_APART(Pattern.compile(
            "Your gloves of silence are going to fall apart!"
    ), (final PickPocketPlugin p, final Matcher matcher) -> {
        p.getConfig().glovesOfSilenceCharge("=1");
        return true;
    }),

    HAVE_WORN_OUT(Pattern.compile(
            "Your gloves of silence have worn out\\."
    ), (final PickPocketPlugin p, final Matcher matcher) -> {
        p.getConfig().glovesOfSilenceCharge("=62");
        return true;
    });


    private Pattern pattern = null;
    private BiFunction<PickPocketPlugin, Matcher, Boolean> method = null;

    MessageMapper(final Pattern pattern, final BiFunction<PickPocketPlugin, Matcher, Boolean> method)
    {
        this.pattern = pattern;
        this.method = method;
    }

    public final boolean callMethodIfFound(final PickPocketPlugin p, final String message) {
        final Matcher matcher = getPattern().matcher(message);
        if (matcher.find())
        {
            return getMethod().apply(p, matcher);
        }
        return false;
    }
}
