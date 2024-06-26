package com.anotherpillow.skyplusplus.client;

import com.anotherpillow.skyplusplus.commands.ConfigCommand;
import com.anotherpillow.skyplusplus.features.DiscordRPC;
import com.anotherpillow.skyplusplus.features.ShowEmptyShops;
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
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
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
import com.anotherpillow.skyplusplus.features.BetterChangeBiome;
import com.anotherpillow.skyplusplus.features.BetterCrateKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Objects;

public class SkyPlusPlusClient implements ClientModInitializer {
    private BlockPos lastPos;
    private boolean inSpawn = false;

    // Credit for some of this from https://github.com/MeteorDevelopment/meteor-client/blob/master/src/main/java/meteordevelopment/meteorclient/MeteorClient.java#L45
    public static final String MOD_ID = "skyplusplus";
    public static final ModMetadata MOD_META;
    public static final String NAME;
    public static final Logger LOG;
    public static final String VERSION;

    static {
        MOD_META = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow().getMetadata();
        NAME = MOD_META.getName();
        LOG = LoggerFactory.getLogger(NAME);
        VERSION = MOD_META.getVersion().getFriendlyString();
    }

    @Override
    public void onInitializeClient() {
        SkyPlusPlusConfig.configInstance.load();
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
        BetterChangeBiome.register();
        BetterCrateKeys.register();
        ShowEmptyShops.register();

        if (config.enableDiscordRPC)
            DiscordRPC.start();


        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            if (config.enableTraderFinder && Server.onSkyblock()) {
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
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player == null) return;

            if (config.enableDiscordRPC)
                DiscordRPC.onTick();

            BlockPos pos = new BlockPos(player.getX(), player.getY(), player.getZ());
            if (pos.equals(lastPos)) return;

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
            ConverterCommand.register(dispatcher);
            SmartTPCommand.register(dispatcher);
            ConfigCommand.register(dispatcher);
        });

    }
}
