package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.util.Chat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
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

        Chat.sendCommandToServer("unlock");
        Chat.sendCommandToServer("tpahere " + username);


        awaitingLock = true;

        locktask = new TimerTask() {
            @Override
            public void run() {
                awaitingLock = false;
                Chat.send(Chat.addLogo(Text.translatable("skyplusplus.smarttp.timed-out", username)));

                Chat.sendCommandToServer("lock");
            }
        };

        locktimer.schedule(locktask, 120000);

    }

    public static void lock() {
        MinecraftClient client = MinecraftClient.getInstance();

        if (locktask != null) {
            locktask.cancel();
            locktask = null;
        }

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                awaitingLock = false;
                Chat.sendCommandToServer("lock");
            }
        };

        timer.schedule(task, 500);

    }
}
