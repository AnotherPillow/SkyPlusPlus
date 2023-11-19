package com.anotherpillow.skyplusplus.util;

import java.util.Arrays;
import java.util.List;

public class Server {
    public static boolean isSkyblock(String domain) {
        List<String> skyblockDomains = Arrays.asList(
                "skyblock.net",
                "skyblock.org",
                "skyblock.com",
                "mc.skyblock.net",
                "skyblock.in",
                "skyblock.ninja",
                "skyblock.info",
                "c.skyblock.net",
                "skyblock.rocks",
                "skyblock.one",
                "skyblock.biz",
                "server.skyblock.net",
                "playskyblocknetwork.com",
                "skyblock-hub.com",
                "skyblock.city",
                "skyblock.click",
                "skyblock.cloud",
                "skyblock.cool",
                "skyblock.date",
                "skyblock.fun",
                "skyblock.info",
                "skyblock.men",
                "skyblock.sbs",
                "skyblock.today",
                "skyblock.trade",
                "skyblock.work",
                "skyblock.zone",
                "skyblocke.com",
                "skyblockgames.com",
                "skyblockia.com",
                "skyblockia.org",
                "skyblockia.net"
        );

        return skyblockDomains.contains(domain);

    }
}
