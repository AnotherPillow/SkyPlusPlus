package com.anotherpillow.skyplusplus.util;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
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
        MOBARENA,

    }
    public static boolean isSkyblock(String domain) {
        List<String> skyblockDomains = Arrays.asList(
                "skyblock.net",
                "skyblock.org",
                "skyblock.com",
                "skyblock.ninja",
                "skyblock.rocks",
                "skyblock.one",
                "skyblock.biz",
                "playskyblocknetwork.com",
                "skyblock-hub.com",
                "skyblock.city",
                "skyblock.cloud",
                "skyblock.date",
                "skyblock.fun",
                "skyblock.info",
                "skyblock.men",
                "skyblock.today",
                "skyblock.trade",
                "skyblock.work",
                "skyblock.zone",
                "skyblocke.com",
                "skyblockgames.com",
                "skyblockia.com",
                "skyblockia.org",
                "skyblockia.net",
                "sky-block.net",
                "sky-block.org",
                "skyblock.foo",
                "148.113.154.207",
                "skyblock.day",
                "skyblock.dad",
                "hypixel-skyblock.com",
                "hypixel-skyblock.net",
                "originalskyblock.com",
                "skyblock-hypixel.com",
                "hypixelsb.com",
                "groundblock.org",
                "skyblock.onl"
        );

        return skyblockDomains.contains(domain) || skyblockDomains.stream().anyMatch(domain::endsWith);
    }

    public static Mode getSkyblockMode() {
        if (!onSkyblock()) return Mode.NONE;
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        if (client.world == null) return Mode.NONE;
        int simulationDistance = client.world.getSimulationDistance();

        return switch (simulationDistance) {
            case 5 -> Mode.SURVIVAL;
            case 3 -> Mode.ECONOMY;
            case 64 -> Mode.CLASSIC; // could technically be skywars too, that's a TODO
            case 10 -> player == null ? Mode.UNKNOWN :
                (Math.abs(player.getY()) > 11000 ? Mode.MOBARENA : Mode.HUB); // mobarena is around -12k,-12k but hub is at 0,0)
            case 8 -> Mode.EVENT;
            default -> Mode.UNKNOWN;
        };

    }
    public static boolean onSkyblock() {
        ServerInfo server = MinecraftClient.getInstance().getCurrentServerEntry();
        return isSkyblock(server == null ? "" : server.address);
    }
}
