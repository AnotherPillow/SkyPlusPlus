package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public class SmartTP {
    public static boolean awaitingLock = false;

    public static void teleport(String username) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        if (player == null) return;
        player.sendCommand("unlock", Text.empty());
        player.sendCommand("tpahere " + username, Text.empty());
        awaitingLock = true;

    }

    public static void lock() {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        if (player == null) return;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                awaitingLock = false;

                player.sendCommand("lock", Text.empty());
            }
        }, 500);

    }
}
