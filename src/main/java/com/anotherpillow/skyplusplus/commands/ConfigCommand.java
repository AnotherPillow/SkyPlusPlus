package com.anotherpillow.skyplusplus.commands;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;

public class ConfigCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        MinecraftClient client = MinecraftClient.getInstance();

        dispatcher.register(ClientCommandManager.literal("skyplusplus").executes(context -> {
            client.send(() -> {
                client.setScreen(SkyPlusPlusConfig.getConfigScreen(client.currentScreen));
            });
            return 1;
        }));
    }
}
