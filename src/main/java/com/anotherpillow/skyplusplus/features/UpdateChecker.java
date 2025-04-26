package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.util.Chat;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class UpdateChecker {
    public static URI API_URL = URI.create("https://api.modrinth.com/v2/project/sky++/version?game_version=1.19.2");
    public static String latest = null;

    public static String getLatestVersion() {
        try {
            HttpClient http = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS)
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(API_URL)
                    .header("Content-Type", "application/json")
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .header("User-Agent", "skyplusplus update checker " + SkyPlusPlusClient.VERSION)
                    .GET()
                    .build();

            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

            JsonArray versionsArray = JsonParser.parseString(response.body()).getAsJsonArray();

            return versionsArray.get(0).getAsJsonObject().get("version_number").getAsString();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isVersionOutdated(String currentVersion, String remoteVersion) {
        try {
            int[] cvParts = Arrays.stream(currentVersion.split("\\."))
                .mapToInt(Integer::parseInt)
                .toArray();
            int[] rvParts = Arrays.stream(remoteVersion.split("\\."))
                .mapToInt(Integer::parseInt)
                .toArray();
            int maxLength = Math.max(cvParts.length, rvParts.length);

            for (int i = 0; i < maxLength; i++) {
                int current = i < cvParts.length ? cvParts[i] : 0;
                int latest = i < rvParts.length ? rvParts[i] : 0;
                if (current != latest) {
                    return current < latest;
                }
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkForUpdate() {
        String _latest = latest == null ? getLatestVersion() : latest;
        if (_latest == null) return false;
        latest = _latest;
        return isVersionOutdated(SkyPlusPlusClient.VERSION, latest);
    }

    public static void onServerJoin() {
        if (checkForUpdate()) Chat.send(Chat.addLogo("Sky++ " + SkyPlusPlusClient.VERSION + " is outdated. Version " + latest + " is available."));
    }
}