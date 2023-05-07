package com.anotherpillow.skyplusplus.util;

import java.util.regex.Pattern;

public class StringChecker {
    public static Pattern welcomeIslandPattern = Pattern.compile("============ Welcome to [A-Za-z0-9_]+'s Island ============");
    public static Pattern visitingIslandPatten = Pattern.compile("Visiting [A-Za-z0-9_]+'s island");
    public static Pattern skyblockMessagePattern = Pattern.compile("^\\[Skyblock] .+$");
    public static Pattern AFKMessagePattern = Pattern.compile("^\\* \\[[A-Za-z]+] [A-Za-z0-9_]+ is no(w| longer) AFK\\.?$");
    public static Pattern newUserMessagePattern = Pattern.compile("^Welcome [A-Za-z0-9_] to Skyblock!$");
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
}
