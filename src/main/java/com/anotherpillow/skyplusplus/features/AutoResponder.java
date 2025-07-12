package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;


public class AutoResponder {
    public static void respond() {
        MinecraftClient client = MinecraftClient.getInstance();
        //? if >1.19.2 {
        ClientPlayNetworkHandler handler = client.getNetworkHandler();
        if (handler == null) return;
        handler.sendCommand("r " + SkyPlusPlusClient.config.autoResponderMessage);
        //?} else {
        /*ClientPlayerEntity player = client.player;
        if (player == null) return;
        player.sendCommand("r " + SkyPlusPlusClient.config.autoResponderMessage, Text.empty());
         *///?}

    }
}
