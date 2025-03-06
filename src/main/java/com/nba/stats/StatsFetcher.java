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


public class StatsFetcher {
    private static JSONArray playersData; // Stores loaded player data

    // Static block to load JSON data once when the class is used
    static {
        try {
            // Load the JSON file from the correct directory
            String filePath = "src/main/resources/nba_stats.json";
            String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            playersData = new JSONArray(content); // Parse JSON data

            System.out.println("✅ Data loaded successfully. Total players: " + playersData.length());
        } catch (Exception e) {
            System.err.println("❌ ERROR: Could not load player data.");
            e.printStackTrace();
            playersData = new JSONArray(); // Ensure program doesn't crash on failure
        }
    }


    public static String normalizeName(String name) {
        if (name == null || name.trim().isEmpty()) return ""; // Handle empty input safely
        return Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "") // Remove accents
                .replaceAll("[^a-zA-Z ]", "") // Remove non-letter characters (except spaces)
                .toLowerCase()
                .trim();
    }

    /**
     * Fetches statistics for a given player by normalizing the search input and comparing it to the loaded data.
     *
     * @param playerName The name of the player being searched.
     * @return A map containing player stats if found, or null if no match exists.
     */
    public static Map<String, Object> fetchStats(String playerName) {
        Map<String, Object> playerStats = new HashMap<>();

        if (playerName == null || playerName.trim().isEmpty()) {
            System.err.println("❌ ERROR: Invalid player name.");
            return null;
        }

        String normalizedSearch = normalizeName(playerName);
        System.out.println("🔍 Searching for: \"" + playerName + "\" (normalized: \"" + normalizedSearch + "\")");

        // Loop through all player data and check for matches
        for (int i = 0; i < playersData.length(); i++) {
            try {
                JSONObject player = playersData.getJSONObject(i);
                String playerNormalized = normalizeName(player.getString("player").trim());

                System.out.println("🔎 Checking player: " + playerNormalized);

                if (playerNormalized.equals(normalizedSearch)) {
                    // Store player stats if found
                    playerStats.put("player", player.getString("player"));
                    playerStats.put("points", player.getInt("points"));
                    playerStats.put("rebounds", player.getInt("rebounds"));
                    playerStats.put("assists", player.getInt("assists"));

                    System.out.println(" Player found: " + player.getString("player"));
                    return playerStats;
                }
            } catch (JSONException e) {
                System.err.println(" ERROR: Invalid JSON format at index " + i);
                e.printStackTrace();
            }
        }

        // If no match is found
        System.out.println(" No stats found for \"" + playerName + "\".");
        return null;
    }
}
