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
                "skyblock.foo"
        );

        return skyblockDomains.contains(domain);
    }

    private static java.util.concurrent.CompletableFuture<com.mojang.brigadier.suggestion.Suggestions> suggestionsFuture = null;

    public static Mode getSkyblockMode(CommandDispatcher<FabricClientCommandSource> dispatcher) throws ExecutionException, InterruptedException, TimeoutException {
        if (!onSkyblock()) return Mode.NONE;
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player != null && suggestionsFuture != null && suggestionsFuture.isDone()) {
            Chat.send(String.valueOf(suggestionsFuture.getNow(null).getList()));
        } else if (suggestionsFuture == null && client.player != null) {
            suggestionsFuture =
                    dispatcher.getCompletionSuggestions(dispatcher.parse("auctionhouse",
                            (FabricClientCommandSource) client.player.networkHandler.getCommandSource()));
        }

            /*suggestionsFuture.thenRun(() -> {
                if (suggestionsFuture.isDone()) {
                    try {
                        Chat.send(String.valueOf(suggestionsFuture.get(5, TimeUnit.SECONDS)));
                    } catch (ExecutionException e) {} catch (InterruptedException e) {} catch (TimeoutException e) {}

                }
            });*/

        return Mode.UNKNOWN;

    }
    public static boolean onSkyblock() {
        ServerInfo server = MinecraftClient.getInstance().getCurrentServerEntry();
        return isSkyblock(server == null ? "" : server.address);
    }
}
