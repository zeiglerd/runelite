package net.runelite.client.plugins.osrstracker;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.http.api.hiscore.HiscoreClient;
import net.runelite.http.api.hiscore.HiscoreResult;
import net.runelite.http.api.hiscore.HiscoreSkill;
import org.json.*;

@PluginDescriptor(
        name = "OSRS Tracker",
        description = "PUT earned experience -- to api-php-osrstracker -- upon level up, and once every ten minutes.",
        tags = {"hiscores", "players", "skills"},
        loadWhenOutdated = true
)
@Slf4j
public class OsrsTrackerPlugin extends Plugin {
    @Subscribe
    protected void onChatMessage(ChatMessage event) {
        String chatMessage = event.getMessage();

        if (LEVEL_PATTERN.matcher(chatMessage).matches()) {
            putSkillsText();
        }
    }

    @Subscribe
    protected void onGameStateChanged(GameStateChanged event) {
        switch (event.getGameState()) {
//            case LOGGED_IN:
//                // @TODO We should call lookup() here; but, player name does not seem to be defined yet
//                hiscoreResults = lookup();
//                break;
            case LOGIN_SCREEN:
                hiscoreResults = null;
                lastPut = Integer.toUnsignedLong(-1);
                break;
            default:
        }
    }

    @Subscribe
    protected void onGameTick(GameTick event) {
        if (hiscoreResults == null) {
            // @TODO We should call lookup() when LOGGED_IN; but, player name does not seem to be defined by then
            hiscoreResults = lookup();
        }

        long currentTime = System.currentTimeMillis();

        if (hiscoreResults != null && lastPut < (
                currentTime - (DEV ? PUT_INTERVAL_DEV : PUT_INTERVAL_DEFAULT)
        )) {
            lastPut = currentTime;

            putSkillsText();
        }
    }

    @Override
    protected void shutDown() throws Exception {
    }

    @Override
    protected void startUp() throws Exception {
    }

    private void putSkillsText() {
        sendData(getSkillsText());
    }

    private String getSkillsText() {
        ArrayList<HiscoreSkill> hiscoreSkills = getHiscoreSkills();
        ArrayList<Skill> skills = getSkills();

        StringBuilder skillString = new StringBuilder();

        for (Integer i = 0; i < hiscoreSkills.size(); i++) {
            HiscoreSkill hiscoreSkill = hiscoreSkills.get(i);

            Integer level;
            Integer rank;
            Long experience = null;

            if (i < skills.size()) {
                level = getSkillLevel(i);
                rank = getSkillRank(i);
                experience = getSkillExperience(i);
            } else {
                net.runelite.http.api.hiscore.Skill hiscoreResult = hiscoreResults.getSkill(hiscoreSkill);

                level = hiscoreResult.getLevel();
                rank = hiscoreResult.getRank();
            }

            skillString.append(skillString.length() > 0 ? " " : "");
            skillString.append(rank);
            skillString.append(",");
            skillString.append(level);
            skillString.append(experience == null ? "" : "," + experience);
        }

        return skillString.toString();
    }

    protected HiscoreResult lookup() {
        HiscoreResult hiscoreResults = null;

        try {
            hiscoreResults = hiscoreClient.lookup(getOsrsn());
        } catch (Exception ex) {
            log.warn("Error fetching hiscore data", ex);
        }

        return hiscoreResults;
    }

    private String getOsrsn() {
        return client.getLocalPlayer().getName();
    }

    private ArrayList<HiscoreSkill> getHiscoreSkills() {
        return new ArrayList<>(Arrays.asList(HiscoreSkill.values()));
    }

    protected ArrayList<Skill> getSkills() {
        ArrayList<Skill> skills = new ArrayList<>(Arrays.asList(Skill.values()));

        // Overall first
        skills.add(0, skills.get(skills.size() - 1));
        skills.remove(skills.size() - 1);

        return skills;
    }

    protected Long getSkillExperience(Integer index) {
        return getSkillExperiences().get(index);
    }

    private ArrayList<Long> getSkillExperiences() {
        ArrayList<Skill> skills = getSkills();

        ArrayList<HiscoreSkill> hiscoreSkills = getHiscoreSkills();

        // Does not include overall
        int[] skillExperiences = client.getSkillExperiences();

        ArrayList<Long> experiences = new ArrayList<>();

        Long overall = Integer.toUnsignedLong(0);

        for (int i = 1; i < skills.size(); i++) {
            HiscoreSkill hiscoreSkill = hiscoreSkills.get(i);

            Long hiscoreExperience = hiscoreResults.getSkill(hiscoreSkill).getExperience();

            Integer skillExperience = skillExperiences[i - 1];

            Long experience = hiscoreExperience < skillExperience ? skillExperience : hiscoreExperience;

            experiences.add(experience);

            overall += experience;
        }

        experiences.add(0, overall);

        return experiences;
    }

    private Integer getSkillLevel(Integer index) {
        return getSkillLevels().get(index);
    }

    private ArrayList<Integer> getSkillLevels() {
        ArrayList<Skill> skills = getSkills();

        ArrayList<HiscoreSkill> hiscoreSkills = getHiscoreSkills();

        // Does not include overall
        int[] skillLevels = client.getRealSkillLevels();

        ArrayList<Integer> levels = new ArrayList<>();

        Integer overall = 0;

        for (int i = 1; i < skills.size(); i++) {
            HiscoreSkill hiscoreSkill = hiscoreSkills.get(i);

            Integer hiscoreLevel = hiscoreResults.getSkill(hiscoreSkill).getLevel();

            int skillLevel = skillLevels[i - 1];

            Integer level = hiscoreLevel < skillLevel ? skillLevel : hiscoreLevel;

            levels.add(level);

            overall += level;
        }

        levels.add(0, overall);

        return levels;
    }

    private Integer getSkillRank(Integer index) {
        return getSkillRanks().get(index);
    }

    private ArrayList<Integer> getSkillRanks() {
        ArrayList<Skill> skills = getSkills();

        ArrayList<HiscoreSkill> hiscoreSkills = getHiscoreSkills();

        ArrayList<Integer> ranks = new ArrayList<>();

        for (int i = 0; i < skills.size(); i++) {
            HiscoreSkill hiscoreSkill = hiscoreSkills.get(i);

            Integer hiscoreRank = hiscoreResults.getSkill(hiscoreSkill).getRank();

            ranks.add(hiscoreRank);
        }

        return ranks;
    }

    private String sendData(String skills) {
        try {
            URL url = new URL(API_PROTOCOL, DEV ? API_HOST_DEV : API_HOST_DEFAULT, API_ROUTE);

            if (DEV && VERBOSE == "vvv") {
                log.info("url: " + url);
            }

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod(PUT);
            con.setRequestProperty("Accept", CONTENT_TYPE_JSON);
            con.setRequestProperty("Content-Type", CONTENT_TYPE_JSON + "; charset=" + StandardCharsets.UTF_8);

            OutputStream os = con.getOutputStream();

            JSONObject put = new JSONObject();

            put.put("apiKey", API_KEY);
            put.put("method", PUT);
            put.put("osrsn", getOsrsn());
            put.put("skills", skills);
            put.put("source", API_SOURCE);
            put.put("type", API_TYPE);

            os.write(put.toString().getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

            if (DEV && VERBOSE == "vvv") {
                log.info("put: " + put);
            }

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8)
                );

                String line;

                JSONObject response = null;

                while ((line = br.readLine()) != null) {
                    response = new JSONObject(line);
                }

                br.close();

                if (DEV && VERBOSE == "vvv") {
                    log.info("res: " + response);
                }

                return response.getString("textMessage");
            } else {
                log.warn("response: " + con.getResponseMessage() + "; responseCode: " + responseCode);
            }
        } catch (Exception e) {
            log.warn("Simply failed to sendData", e.getMessage());
        }

        return "";
    }

    @Inject
    protected Client client;

    private static HiscoreResult hiscoreResults;
    private static long lastPut;
    private static final HiscoreClient hiscoreClient = new HiscoreClient();

    private static final String CONTENT_TYPE_JSON = "application/json";

    private static final String API_HOST_DEFAULT = "74.208.177.38";
    private static final String API_HOST_DEV = "127.0.0.1";
    private static final String API_KEY = "ctV36wbwbpQT2r67KGykg6J44Hm8f27v";
    private static final String API_PROTOCOL = "http";
    private static final String API_ROUTE = "/api/";
    private static final String API_SOURCE = "RuneLite";
    private static final String API_TYPE = "skills";

    private static final String PUT = "PUT";

    private static final Integer PUT_INTERVAL_DEFAULT = 1000 * 10 * 60; // Ten minutes
    private static final Integer PUT_INTERVAL_DEV = 1000 * 10; // Ten seconds

    private static final Pattern LEVEL_PATTERN = Pattern.compile(
        ".*advanced your [a-zA-Z]+ level\\. You are now level \\d+\\."
    );

    private static final Boolean DEV = false; // false | true
    private static final String VERBOSE = "vvv"; // "" | "vvv"
}
