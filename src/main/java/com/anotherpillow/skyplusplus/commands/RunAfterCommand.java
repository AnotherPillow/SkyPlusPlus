package com.anotherpillow.skyplusplus.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public class RunAfterCommand {
    public static TimerTask task = null;
    public static Timer timer = new Timer();
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        MinecraftClient client = MinecraftClient.getInstance();

        dispatcher.register(ClientCommandManager.literal("runafter")
            .then(ClientCommandManager.argument("timeout", IntegerArgumentType.integer())
            .then(ClientCommandManager.argument("command", StringArgumentType.greedyString())
            .executes(context -> {

            int timeout = IntegerArgumentType.getInteger(context, "timeout");
            String command = StringArgumentType.getString(context, "command");

            task = new TimerTask() {
                @Override
                public void run() {

                client.send(() -> {
                    if (client.player == null) return;
                    //? if >1.19.2 {
                    /*if (command.startsWith("/")) client.getNetworkHandler().sendChatMessage(command.substring(1));
                    else client.getNetworkHandler().sendChatMessage(command);
                    *///?} else {
                    if (command.startsWith("/")) client.player.sendCommand(command.substring(1), Text.empty());
                    else client.player.sendChatMessage(command, Text.of(command));
                     //?}

                });

                }
            };

            timer.schedule(task, timeout);

            return 1;
        }))));
    }
}
