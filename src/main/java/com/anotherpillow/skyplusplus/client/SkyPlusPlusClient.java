package com.anotherpillow.skyplusplus.client;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import com.anotherpillow.skyplusplus.util.Server;
import com.anotherpillow.skyplusplus.util.TraderFinder;
import com.anotherpillow.skyplusplus.screen.TraderImage;
import com.anotherpillow.skyplusplus.util.TraderCountdown;
import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.anotherpillow.skyplusplus.commands.ConverterCommand;
import com.anotherpillow.skyplusplus.commands.SmartTPCommand;

import java.util.Objects;

public class SkyPlusPlusClient implements ClientModInitializer {
    private BlockPos lastPos;
    private boolean inSpawn = false;

    @Override
    public void onInitializeClient() {
        SkyPlusPlusConfig.configInstance.load();

        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
            //MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("isSpawn: " + inSpawn));
            //if (MinecraftClient.getInstance().getCurrentServerEntry() == null) return;
            //Server.isSkyblock(MinecraftClient.getInstance().getCurrentServerEntry().address)) {


            // Get the text renderer and draw the custom text on the screen
            //TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            //DrawableHelper.drawCenteredText(matrixStack, textRenderer, MinecraftClient.getInstance().getCurrentServerEntry().address, MinecraftClient.getInstance().getWindow().getScaledWidth() / 2, MinecraftClient.getInstance().getWindow().getScaledHeight() / 2 - 20, 0xFFFFFF);

            if (config.enableTraderFinder) {
                if (inSpawn) {
                    if (!Objects.equals(TraderFinder.traderXYZString, "")) TraderFinder.showTraderString(matrixStack);
                    else TraderFinder.findTrader(matrixStack);
                } else {
                    TraderCountdown.TraderCountdown(matrixStack);
                }

                TraderImage.draw(matrixStack);
            }

        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Get the player entity and current position
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player == null) return;
            BlockPos pos = new BlockPos(player.getX(), player.getY(), player.getZ());

            /*if (MinecraftClient.getInstance().getCurrentServerEntry() == null || !Server.isSkyblock(MinecraftClient.getInstance().getCurrentServerEntry().address)) {
                lastPos = null;
                return;
            }*/

            // Check if the player's position has changed
            if (pos.equals(lastPos)) return;
                // check if lastPos is more than 10 blocks away from pos
            //get config
            SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
            //System.out.println(config.enableTraderFinder);
            if (config.enableTraderFinder) {
                if (lastPos != null && lastPos.getManhattanDistance(pos) > 10) {
                    if (pos.getManhattanDistance(new BlockPos(4000, 175, 2000)) < 5
                            || pos.getManhattanDistance(new BlockPos(0, 175, 0)) < 5) {
                        //MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Player teleported from " + lastPos + " to " + pos + " (Entered Spawn)"));
                        inSpawn = true;

                    } //check if moved 200+ blocks from spawn
                    else if (pos.getManhattanDistance(new BlockPos(4000, 175, 2000)) > 200
                            || pos.getManhattanDistance(new BlockPos(0, 175, 0)) > 200 && inSpawn) {
                        //MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of("Player teleported from " + lastPos + " to " + pos + " (Left Spawn)"));
                        TraderFinder.traderXYZString = "";
                        inSpawn = false;
                    }
                }
            }

            lastPos = pos;

        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            // Register the /sconvert command
            ConverterCommand.register(dispatcher);
            SmartTPCommand.register(dispatcher);

        });


    }
}
