package com.anotherpillow.skyplusplus.commands;

import com.anotherpillow.skyplusplus.features.SmartTP;
import com.anotherpillow.skyplusplus.screen.BedrockBotsScreen;
import com.anotherpillow.skyplusplus.screen.CottonScreenSubclass;
import com.anotherpillow.skyplusplus.util.ChatLogo;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class BedrockBotCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("bedrock-bots").executes(context -> {
            FabricClientCommandSource src = context.getSource();
            // src.sendFeedback(Text.of("Opening..."));

            MinecraftClient client = MinecraftClient.getInstance();

            client.send(() -> {
                client.setScreen(new CottonScreenSubclass(new BedrockBotsScreen()));
            });
            return 1;
        }));


    }
}
