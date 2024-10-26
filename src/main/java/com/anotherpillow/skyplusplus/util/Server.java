package com.anotherpillow.skyplusplus.util;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Server {
    public static enum Mode {
        NONE,
        UNKNOWN,
        HUB,
        SURVIVAL,
        ECONOMY,
        CLASSIC,
        SKYWARS,
        EVENT,

    }
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
                "skyblockia.net",
                "skyblock.in",
                "skyblock.rocks",
                "skyblock.one",
                "sky-block.net",
                "sky-block.org",
                "skyblock.foo",
                "148.113.154.207"
        );

        return skyblockDomains.contains(domain);
    }

    private static java.util.concurrent.CompletableFuture<com.mojang.brigadier.suggestion.Suggestions> suggestionsFuture = null;

    public static Mode getSkyblockMode() {
        if (!onSkyblock()) return Mode.NONE;
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.world == null) return Mode.NONE;
        int simulationDistance = client.world.getSimulationDistance();

        return switch (simulationDistance) {
            case 5 -> Mode.SURVIVAL;
            case 3 -> Mode.ECONOMY;
            case 64 -> Mode.CLASSIC; // could technically be skywars too, that's a TODO
            case 10 -> Mode.HUB;
            case 8 -> Mode.EVENT;
            default -> Mode.UNKNOWN;
        };

    }
    public static boolean onSkyblock() {
        ServerInfo server = MinecraftClient.getInstance().getCurrentServerEntry();
        return isSkyblock(server == null ? "" : server.address);
    }
}
