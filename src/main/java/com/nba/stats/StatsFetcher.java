package com.nba.stats;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles fetching NBA player statistics from a JSON file.
 */
public class StatsFetcher {
    private static JSONArray playersData;

    // Load JSON data once when the class is initialized
    static {
        try {
            String filePath = "src/main/resources/nba_stats.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            playersData = new JSONArray(content);
            System.out.println("✅ Data loaded successfully. Total players: " + playersData.length());
        } catch (Exception e) {
            System.err.println("❌ ERROR: Could not load player data.");
            e.printStackTrace();
            playersData = new JSONArray();
        }
    }

    /**
     * Normalizes player names for better search accuracy.
     */
    public static String normalizeName(String name) {
        if (name == null || name.trim().isEmpty()) return "";
        return Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "") // Remove accents
                .replaceAll("[^a-zA-Z ]", "") // Remove non-letter characters
                .toLowerCase()
                .trim();
    }

    /**
     * Searches for a player's stats and returns them.
     */
    public static Map<String, Object> fetchStats(String playerName) {
        Map<String, Object> playerStats = new HashMap<>();

        if (playerName == null || playerName.trim().isEmpty()) {
            System.err.println("❌ ERROR: Invalid player name.");
            return null;
        }

        String normalizedSearch = normalizeName(playerName);
        System.out.println("🔍 Searching for: \"" + playerName + "\" (normalized: \"" + normalizedSearch + "\")");

        for (int i = 0; i < playersData.length(); i++) {
            try {
                JSONObject player = playersData.getJSONObject(i);
                String playerNormalized = normalizeName(player.getString("player").trim());

                if (playerNormalized.equals(normalizedSearch)) {
                    playerStats.put("player", player.getString("player"));
                    playerStats.put("points", player.getInt("points"));
                    playerStats.put("rebounds", player.getInt("rebounds"));
                    playerStats.put("assists", player.getInt("assists"));

                    System.out.println("✅ Player found: " + player.getString("player"));
                    return playerStats;
                }
            } catch (JSONException e) {
                System.err.println("❌ ERROR: Invalid JSON format at index " + i);
                e.printStackTrace();
            }
        }

        System.out.println("❌ No stats found for \"" + playerName + "\".");
        return null;
    }
}
