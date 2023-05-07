package com.anotherpillow.skyplusplus.util;

import net.minecraft.client.MinecraftClient;

import java.util.regex.Pattern;

public class StringChecker {
    public static Pattern welcomeIslandPattern = Pattern.compile("============ Welcome to [A-Za-z0-9_]+'s Island ============");
    public static Pattern visitingIslandPatten = Pattern.compile("Visiting [A-Za-z0-9_]+'s island");
    public static Pattern skyblockMessagePattern = Pattern.compile("^\\[Skyblock] .+$");
    public static Pattern AFKMessagePattern = Pattern.compile("^\\* \\[[A-Za-z]+] [A-Za-z0-9_]+ is no(w| longer) AFK\\.?$");
    public static Pattern newUserMessagePattern = Pattern.compile("^Welcome [A-Za-z0-9_]+ to Skyblock!$");
    public static Pattern deathMessagePattern = Pattern.compile("^\\[☠] \\d+ players have perished in the void today.$");
    public static Pattern raffleMessagePattern = Pattern.compile("^\\[SBRaffle] \\[[A-Za-z]+] [A-Za-z0-9_]+ just bought \\d ticke.+!$");
    public static Pattern autoResponderPattern = Pattern.compile("^\\[[A-Za-z0-9_]+ -> me] .+$");

    public static boolean welcomeIslandCheck(String input) {
        return welcomeIslandPattern.matcher(input).find() || visitingIslandPatten.matcher(input).find();
    }

    public static boolean skyblockMessageCheck(String input) {
        return skyblockMessagePattern.matcher(input).matches();
    }

    public static boolean AFKMessageCheck(String input) {
        return AFKMessagePattern.matcher(input).matches();
    }

    public static boolean newUserMessageCheck(String input) {
        return newUserMessagePattern.matcher(input).find();
    }

    public static boolean deathMessageCheck(String input) {
        return deathMessagePattern.matcher(input).find();
    }

    public static boolean raffleMessageCheck(String input) {
        return raffleMessagePattern.matcher(input).find();
    }

    public static boolean autoResponderCheck(String input) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return false;

        if (input.startsWith("[" + client.player.getEntityName() + " -> me]")) return false;
        return autoResponderPattern.matcher(input).find();
    }
}
