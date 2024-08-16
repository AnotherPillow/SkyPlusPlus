package com.anotherpillow.skyplusplus.commands;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.anotherpillow.skyplusplus.util.Chat;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;

public class CheckSimulationDistanceCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        MinecraftClient client = MinecraftClient.getInstance();

        dispatcher.register(ClientCommandManager.literal("simdist").executes(context -> {

            assert client.world != null;
            Chat.send("simulation dist: " + client.world.getSimulationDistance());
            return 1;
        }));
    }
}
