package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;


public class AutoResponder {
    public static void respond() {
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        if (player == null) return;

        player.sendCommand("r " + config.autoResponderMessage, Text.empty());
    }
}
