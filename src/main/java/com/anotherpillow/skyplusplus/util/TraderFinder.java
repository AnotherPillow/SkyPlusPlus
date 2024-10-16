package com.anotherpillow.skyplusplus.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.BlockPos;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;

public class TraderFinder {
    //Trader XYZ to location name
    //X4063, Y174, Z2026 (Warp Grass)
    //X17, Y174, Z44 (Bank)
    //X4032, Y170, Z2000 (Daily Rewards)
    //X32, Y174, -13 (Warp Crates)
    //X4059, Y173, Z2017 (Warp Grass)
    //X4032, Y171, Z2014 (Campfire Walkway)
    public static String traderXYZString = "";

    public static void setTraderXYZString(int x, int y, int z) {
        traderXYZString = x + "," + y + "," + z;
    }

    public static String convertXYZToLocationName(int x, int y, int z) {
        Text traderLocation;
        if (x == 4063 && y == 174 && z == 2026) {
            traderLocation = Text.translatable("skyplusplus.trader.location.warpgrass");
        } else if (x == 17 && y == 174 && z == 44) {
            traderLocation = Text.translatable("skyplusplus.trader.location.bank");
        } else if (x == 4032 && y == 170 && z == 2000) {
            traderLocation = Text.translatable("skyplusplus.trader.location.dailyrewards");
        } else if (x == 32 && y == 174 && z == -13) {
            traderLocation = Text.translatable("skyplusplus.trader.location.warpcrates");
        } else if (x == 4059 && y == 163 && z == 2017) {
            traderLocation = Text.translatable("skyplusplus.trader.location.warpgrass-2");
        } else if (x == 4032 && y == 171 && z == 2014) {
            traderLocation = Text.translatable("skyplusplus.trader.location.campfire-walkway");
        } else {
          traderLocation = Text.of(String.valueOf(Text.translatable("skyplusplus.trader.location.unknown", x, y, z)));
        }

        return traderLocation.getString();
    }

    public static void showTraderString(MatrixStack matrixStack) {
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();


        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        String[] traderXYZ = traderXYZString.split(",");
        int traderX = Integer.parseInt(traderXYZ[0]);
        int traderY = Integer.parseInt(traderXYZ[1]);
        int traderZ = Integer.parseInt(traderXYZ[2]);
        //String txt = "Trader at: " + traderX + ", " + traderY + ", " + traderZ;
        String txt = String.valueOf(Text.translatable("skyplusplus.trader.location-reveal", convertXYZToLocationName(traderX, traderY, traderZ)));

        int textWidth = textRenderer.getWidth(txt);
        int textOffset = textWidth / 2;
        DrawableHelper.drawCenteredText(matrixStack, textRenderer, txt, config.traderX + 32 + textOffset,config.traderY + 16, 0xFFFFFF);

    }

    public static void loopTraders(MatrixStack matrixStack) {
        MinecraftClient client = MinecraftClient.getInstance();
        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();

        for (Entity entity : client.world.getEntities()) {
            if (entity.getType() == EntityType.WANDERING_TRADER) {
                WanderingTraderEntity trader = (WanderingTraderEntity) entity;

                int traderX = (int) entity.getX();
                int traderY = (int) entity.getY();
                int traderZ = (int) entity.getZ();

                setTraderXYZString(traderX, traderY, traderZ);
                showTraderString(matrixStack);
            }
        }

    }
    public static void findTrader(MatrixStack matrixStack) {
        MinecraftClient mc = MinecraftClient.getInstance();
        Entity player = mc.player;

        if (player == null || mc.world == null) return;

        BlockPos playerPos = player.getBlockPos();
        loopTraders(matrixStack);

    }
}
