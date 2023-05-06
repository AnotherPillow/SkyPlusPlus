package com.anotherpillow.skyplusplus.util;

import java.util.Arrays;
import java.util.List;

public class Server {
    public static boolean isSkyblock(String domain) {
        List<String> skyblockDomains = Arrays.asList("skyblock.net", "skyblock.org", "skyblock.com", "mc.skyblock.net", "skyblock.in", "skyblock.ninja", "skyblock.info", "c.skyblock.net", "skyblock.rocks", "skyblock.one", "skyblock.biz");

        return skyblockDomains.contains(domain);

    }
}
