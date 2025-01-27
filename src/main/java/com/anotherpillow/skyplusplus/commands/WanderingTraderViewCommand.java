package com.anotherpillow.skyplusplus.commands;

import com.anotherpillow.skyplusplus.screen.WanderingTraderScreen;
import com.anotherpillow.skyplusplus.screen.CottonScreenSubclass;
import com.mojang.brigadier.CommandDispatcher;
import io.github.cottonmc.cotton.gui.client.CottonHud;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;

public class WanderingTraderViewCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("wandering-trader-preview").executes(context -> {
            FabricClientCommandSource src = context.getSource();
            // src.sendFeedback(Text.of("Opening..."));

            MinecraftClient client = MinecraftClient.getInstance();

            client.send(() -> {
//                client.setScreen(new CottonScreenSubclass(new WanderingTraderScreen()));
                CottonHud.add(WanderingTraderScreen.generateRoot(), 128, 128);
            });
            return 1;
        }));


    }
}
