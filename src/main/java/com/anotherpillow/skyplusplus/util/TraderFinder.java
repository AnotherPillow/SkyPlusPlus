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
        String traderLocation;
        if (x == 4063 && y == 174 && z == 2026) {
            traderLocation = "Warp Grass";
        } else if (x == 17 && y == 174 && z == 44) {
            traderLocation = "the Bank";
        } else if (x == 4032 && y == 170 && z == 2000) {
            traderLocation = "Daily Rewards";
        } else if (x == 32 && y == 174 && z == -13) {
            traderLocation = "Warp Crates";
        } else if (x == 4059 && y == 163 && z == 2017) {
            traderLocation = "Warp Grass";
        } else if (x == 4032 && y == 171 && z == 2014) {
            traderLocation = "the Campfire Walkway";
        } else {
            traderLocation = x + "," + y + "," + z;
        }

        return traderLocation;
    }

    public static void showTraderString(MatrixStack matrixStack) {
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();


        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        String[] traderXYZ = traderXYZString.split(",");
        int traderX = Integer.parseInt(traderXYZ[0]);
        int traderY = Integer.parseInt(traderXYZ[1]);
        int traderZ = Integer.parseInt(traderXYZ[2]);
        //String txt = "Trader at: " + traderX + ", " + traderY + ", " + traderZ;
        String txt = "The Trader is at " + convertXYZToLocationName(traderX, traderY, traderZ);

        int textWidth = textRenderer.getWidth(txt);
        int textOffset = textWidth / 2;
        DrawableHelper.drawCenteredText(matrixStack, textRenderer, txt, config.traderX + 32 + textOffset,config.traderY + 16, 0xFFFFFF);

    }



    public static void loopTraders(MatrixStack matrixStack) {
        MinecraftClient client = MinecraftClient.getInstance();
        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();

        //draw trader hitboxes to screen
        for (Entity entity : client.world.getEntities()) {
            if (entity.getType() == EntityType.WANDERING_TRADER) {
//                System.out.println("Trader found at: " + entity.getX() + ", " + entity.getY() + ", " + entity.getZ());
//
//                //get the trader's onscreen position
//                Vec3d pos = camera.getPos();
//                Vec3d entityPos = entity.getPos();
//                System.out.println("Entity position: " + entityPos.x + ", " + entityPos.y + ", " + entityPos.z);
//                System.out.println("Camera position: " + pos.x + ", " + pos.y + ", " + pos.z);
//
//
//
//
//                int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
//                int screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
//
//                int posX = (int) (screenWidth / 2 + x);
//                int posY = (int) (screenHeight / 2 - y);
//
//                System.out.println("Test: " + posX + ", " + posY);
//
//                System.out.println("Trader onscreen at: " + x + ", " + y + ", " + z);

                //Get NBT data from trader
                WanderingTraderEntity trader = (WanderingTraderEntity) entity;

                //System.out.println("Trader Trades: " + trader.getOffers());
                //send coords to screen
                int traderX = (int) entity.getX();
                int traderY = (int) entity.getY();
                int traderZ = (int) entity.getZ();



                /*TradeOfferList tradeOffers = trader.getOffers();
                for (TradeOffer tradeOffer : tradeOffers) {
                    ItemStack firstBuyItem = tradeOffer.getOriginalFirstBuyItem();
                    ItemStack secondBuyItem = tradeOffer.getSecondBuyItem();
                    ItemStack sellItem = tradeOffer.getSellItem();

                    System.out.println("First buy item: " + firstBuyItem);
                    System.out.println("Second buy item: " + secondBuyItem);
                    System.out.println("Sell item: " + sellItem);
                }

                NbtCompound entityData = trader.writeNbt(new NbtCompound());
                System.out.println("Trader NBT: " + entityData);*/

                setTraderXYZString(traderX, traderY, traderZ);
                showTraderString(matrixStack);
            }
        }

    }
    public static void findTrader(MatrixStack matrixStack) {
        // Get the player entity and current position
        MinecraftClient mc = MinecraftClient.getInstance();
        Entity player = mc.player;
        //System.out.println("Finding trader1");

        if (player == null || mc.world == null) return;

        //System.out.println("Finding trader");


        BlockPos playerPos = player.getBlockPos();


        loopTraders(matrixStack);

    }
}
