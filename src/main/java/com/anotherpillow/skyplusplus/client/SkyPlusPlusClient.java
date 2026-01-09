package com.anotherpillow.skyplusplus.client;

import com.anotherpillow.skyplusplus.commands.*;
import com.anotherpillow.skyplusplus.features.*;
import com.anotherpillow.skyplusplus.keybinds.HoverNBTCopy;
import com.anotherpillow.skyplusplus.keybinds.LockSlotBind;
import com.anotherpillow.skyplusplus.keybinds.ShopsTradingBind;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;

import com.anotherpillow.skyplusplus.util.Server;
import com.anotherpillow.skyplusplus.util.TraderFinder;
import com.anotherpillow.skyplusplus.screen.TraderImage;
import com.anotherpillow.skyplusplus.util.TraderCountdown;
import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
//? >=1.21
import org.joml.Matrix3x2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
    public static final Identifier lockId = Identifier.of(MOD_ID, "textures/lockedicon16x.png");
    public static final Identifier cleanAdBackground  = Identifier.of(MOD_ID, "textures/gui/cleanadgui.png");
    public static final Identifier cleanAdProfileBackground  = Identifier.of(MOD_ID, "textures/gui/cleanadgui-profile.png");
    public static final Identifier cleanQuestsMainBackground  = Identifier.of(MOD_ID, "textures/gui/cleanquestsgui-main.png");
    public static final Identifier cleanQuestsShopBackground  = Identifier.of(MOD_ID, "textures/gui/cleanquestsgui-shop.png");

    public static SkyPlusPlusConfig config;
    public static MinecraftClient client;

    static {
        MOD_META = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow().getMetadata();
        NAME = MOD_META.getName();
        LOG = LoggerFactory.getLogger(NAME);
        VERSION = MOD_META.getVersion().getFriendlyString();
    }

    @Override
    public void onInitializeClient() {

        SkyPlusPlusConfig.configInstance.load();
        config = SkyPlusPlusConfig.configInstance.getConfig();
        client = MinecraftClient.getInstance();
        Chatcryption.generateKeysAndSave();

        BetterChangeBiome.register();
        BetterCrateKeys.register();
        ShowEmptyShops.register();
        CleanGuiBackgrounds.register();

        HoverNBTCopy.register();
        LockSlotBind.register();
        ShopsTradingBind.register();

        try {
            SlotLocker.load();
        } catch (IOException e) {
            LOG.error("Failed to load saved SlotLocker settings.");
        }

        if (config.enableDiscordRPC)
            DiscordRPC.start();


        // drawcontext >= 1.20.1, matrixstack 1.19.x
        HudRenderCallback.EVENT.register((renderObject, tickDelta) -> {
            if (config.enableTraderFinder && Server.onSkyblock()) {
                if (inSpawn) {
                    if (!Objects.equals(TraderFinder.traderXYZString, "")) TraderFinder.showTraderString(renderObject);
                    else TraderFinder.findTrader(renderObject);
                } else {
                    TraderCountdown.DrawCountdown(renderObject);
                }


                //? <1.21 && >=1.20.1 {
                /*TraderImage.draw(renderObject.getMatrices());
                *///?} else if <1.20.1 {
                /*TraderImage.draw(renderObject);
                *///?}


                //? >=1.21 {
                Matrix3x2f m = new Matrix3x2f();
                renderObject.getMatrices().get(m);
                TraderImage.draw(m);
                //?}

            }

        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player == null) return;

            if (config.enableDiscordRPC)
                DiscordRPC.onTick();

            //? if >1.19.2 {
            BlockPos pos = new BlockPos((int) player.getX(), (int) player.getY(), (int) player.getZ());
            //?} else {
            /*BlockPos pos = new BlockPos(player.getX(), player.getY(), player.getZ());
             *///?}
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
            CheckSimulationDistanceCommand.register(dispatcher);
            RunAfterCommand.register(dispatcher);
            GetHeadTextureCommand.register(dispatcher);
            GetNBTJsonCommand.register(dispatcher);
            ShareCommand.register(dispatcher);
            SinkholeCommand.register(dispatcher);
            GetNearbyPlayersCommand.register(dispatcher);
        });

        AttackBlockCallback.EVENT.register((PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) -> {
            if (!config.enableToolSaver) return ActionResult.PASS;
            return ToolSaver.checkHand(player, world, hand);
        });

        UseBlockCallback.EVENT.register((PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) -> {
            if (!config.enableToolSaver) return ActionResult.PASS;
            return ToolSaver.checkHand(player, world, hand);
        });
    }
}
