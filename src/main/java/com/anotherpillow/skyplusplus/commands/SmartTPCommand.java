package com.anotherpillow.skyplusplus.commands;

import com.anotherpillow.skyplusplus.features.SmartTP;

import com.anotherpillow.skyplusplus.util.Chat;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class SmartTPCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        MinecraftClient client = MinecraftClient.getInstance();

        dispatcher.register(ClientCommandManager.literal("smarttp")
            .then(ClientCommandManager.argument("username", StringArgumentType.string()).executes(context -> {
                FabricClientCommandSource src = context.getSource();
                String username = StringArgumentType.getString(context, "username");



                src.sendFeedback(Text.of(Chat.addLogo("Unlocking and teleporting " + username + "...")));
                SmartTP.teleport(username);



                //src.sendFeedback(Text.of(ChatLogo.logo + "Converting " + amount + " " + type + "s..."));

                return 1;
            }))
        );
    }
}

