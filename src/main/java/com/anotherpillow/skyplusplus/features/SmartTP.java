package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.util.Chat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public class SmartTP {
    public static boolean awaitingLock = false;
    public static TimerTask locktask = null;
    public static Timer locktimer = new Timer();

    public static void teleport(String username) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        if (player == null) return;
        player.sendCommand("unlock", Text.empty());
        player.sendCommand("tpahere " + username, Text.empty());
        awaitingLock = true;

        locktask = new TimerTask() {
            @Override
            public void run() {
                awaitingLock = false;
                Chat.send(Chat.addLogo(Text.translatable("skyplusplus.smarttp.timed-out", username)));

                player.sendCommand("lock", Text.empty());
            }
        };

        locktimer.schedule(locktask, 120000);

    }

    public static void lock() {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        if (player == null) return;

        if (locktask != null) {
            locktask.cancel();
            locktask = null;
        }

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                awaitingLock = false;

                player.sendCommand("lock", Text.empty());
            }
        };

        timer.schedule(task, 500);

    }
}
