package com.anotherpillow.skyplusplus.util;

import net.minecraft.client.MinecraftClient;

import java.util.Objects;
import java.util.regex.Pattern;

public class StringChecker {
    public static Pattern welcomeIslandPattern = Pattern.compile("============ Welcome to [A-Za-z0-9_\\.]+'s Island ============");
    public static Pattern visitingIslandPatten = Pattern.compile("Visiting [A-Za-z0-9_\\.]+'s island");
    public static Pattern skyblockMessagePattern = Pattern.compile("^\\[Skyblock] .+$");
    public static Pattern broadcastMessagePattern = Pattern.compile("^\\[Broadcast] .+$");
    public static Pattern AFKMessagePattern = Pattern.compile("^\\* \\[[A-Za-z]+] [A-Za-z0-9_\\.]+ is no(w| longer) AFK\\.?$");
    public static Pattern newUserMessagePattern = Pattern.compile("^Welcome [A-Za-z0-9_\\.]+ to Skyblock!$");
    public static Pattern deathMessagePattern = Pattern.compile("^\\[☠] \\d+ players have perished in the void today.$");
    public static Pattern deathMessagePattern2 = Pattern.compile("^\\[☠] \\d+ players have slipped and fallen into the void today.$");
    public static Pattern raffleMessagePattern = Pattern.compile("^\\[SBRaffle] \\[[A-Za-z]+] [A-Za-z0-9_]+ just bought \\d ticke.+!$");
    public static Pattern autoResponderPattern = Pattern.compile("^\\[[A-Za-z0-9_\\.]{2,16} -> me] .+$");
    public static Pattern advancementMessagePatten = Pattern.compile("^[A-Za-z0-9_\\.]+ has made the advancement \\[.+]");
    public static Pattern TPAcceptMessagePattern = Pattern.compile("^.+ accepted your teleport request\\.$");
    public static Pattern luckyCratesMessagePattern = Pattern.compile("^Lucky Crates .+$");
    public static Pattern mailNotificationMessagePattern = Pattern.compile("^You have \\d+ messages! Type /mail read to view your mail\\.$");
    public static Pattern voteMessagePattern = Pattern.compile("\\[Vote\\] [A-Za-z0-9_\\.]+ voted at vote\\d?\\.skyblock\\.net for \\dx Grass Blocks? and \\dx Voter Keys?!");
    public static Pattern playersOnlineJoinPattern = Pattern.compile("Players online: \\(\\d+/\\d+\\) - World Time: \\d+:\\d+ [A-Z]M");
    public static Pattern raffleWinPattern = Pattern.compile("\\[SBRaffle\\] Congratulations go to [A-Z0-9_\\.]+ for winning [0-9\\.]+$ with \\d tickets");
    public static Pattern visitingTitlePattern = Pattern.compile("§6\\-=§e[A-Za-z0-9_\\.]{1,16}'s Island§6=\\-"); // even names ending with "s" still have "'s"

    public static String COLOUR_CODE_STRING = "[&§][0-9a-fkrl-o]";
    public static Pattern COLOUR_CODE = Pattern.compile(COLOUR_CODE_STRING);

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
        return deathMessagePattern.matcher(input).find() || deathMessagePattern2.matcher(input).find();
    }

    public static boolean raffleMessageCheck(String input) {
        return raffleMessagePattern.matcher(input).find();
    }

    public static boolean autoResponderCheck(String input) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return false;

        //? if >=1.20.4 {
        /*if (input.startsWith("[" + client.player.getName().getString() + " -> me]")) return false;
        *///?} else {
        if (input.startsWith("[" + client.player.getEntityName() + " -> me]")) return false;
         //?}

        return autoResponderPattern.matcher(input).find();
    }

    public static boolean advancementMessageCheck(String input) { return advancementMessagePatten.matcher(input).find(); }

    public static boolean broadcastMessageCheck(String input) { return broadcastMessagePattern.matcher(input).find(); }

    public static boolean TPAcceptCheck(String input) { return TPAcceptMessagePattern.matcher(input).find(); }

    public static boolean expandMessageCheck(String input) { return Objects.equals(input, "You can not build in this area. An expansion pack is needed to access a larger Skyblock space. http://shop.skyblock.net/"); }

    public static boolean luckyCratesMessageCheck(String input) { return luckyCratesMessagePattern.matcher(input).find(); }

    public static boolean mailMessageCheck(String input) { return mailNotificationMessagePattern.matcher(input).find(); }

    public static boolean voteMessageCheck(String input) { return voteMessagePattern.matcher(input).find(); }

    public static boolean playersOnlineJoinCheck(String input) { return playersOnlineJoinPattern.matcher(input).find(); }

    public static boolean raffleWinCheck(String input) { return raffleWinPattern.matcher(input).find(); }

    public static boolean visitingTitleCheck(String input) { return visitingTitlePattern.matcher(input).find(); }

    public static boolean colourCodeCheck(String input) { return COLOUR_CODE.matcher(input).find(); }

}
