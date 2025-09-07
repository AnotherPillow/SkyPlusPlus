package com.anotherpillow.skyplusplus.commands;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;

import java.util.Timer;
import java.util.TimerTask;

public class SinkholeCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        MinecraftClient client = MinecraftClient.getInstance();

        dispatcher.register(ClientCommandManager.literal("sky++_sinkhole")
            .then(ClientCommandManager.argument("extra", StringArgumentType.greedyString())
                    .executes(context -> {
                        return 1;
                    })
            )
        );
    }
}