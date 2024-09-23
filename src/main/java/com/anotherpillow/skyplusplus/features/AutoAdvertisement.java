package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.SkyPlusPlus;
import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.util.Server;
import com.anotherpillow.skyplusplus.util.TimeConsts;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.apache.logging.log4j.Level;

import java.sql.Time;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class AutoAdvertisement {
    public static String MARKER_STRING = "&x&6&5&6&1&D&5"; // string placed at the end to identify messages automatically sent.

    public static boolean isAcceptableTime(int ms) {
        if (Server.getSkyblockMode() == Server.Mode.ECONOMY) return ms >= TimeConsts.MINIMUM_ECONOMY;
        else return  ms >= TimeConsts.MINIMUM_SKYBLOCK;
    }

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static ScheduledFuture<?> scheduledFuture;

    public static void startTask(long interval, TimeUnit timeUnit) {
        ClientPlayerEntity player = SkyPlusPlusClient.client.player;

        Runnable task = () -> {
            Server.Mode mode = Server.getSkyblockMode();

            if (mode == Server.Mode.ECONOMY) player.sendChatMessage(SkyPlusPlusClient.config.economyAdMessage + MARKER_STRING, Text.empty());
            if (mode == Server.Mode.SURVIVAL) player.sendChatMessage(SkyPlusPlusClient.config.survivalAdMessage + MARKER_STRING, Text.empty());
        };

        scheduledFuture = scheduler.scheduleAtFixedRate(task, 0, interval, timeUnit);
    }

    public static void stopTask() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            SkyPlusPlus.log(Level.INFO, "Stopped advertisement task.");
        }
    }

    public static void onServerJoin() {
        switch (Server.getSkyblockMode()) {
            case ECONOMY -> {
                if (!SkyPlusPlusClient.config.enableEconomyAdverts) return;
                stopTask();
                startTask((long) SkyPlusPlusClient.config.economyAdInterval * TimeConsts.Second, TimeUnit.MILLISECONDS);
            }
            case SURVIVAL -> {
                if (!SkyPlusPlusClient.config.enableSurvivalAdverts) return;
                stopTask();
                startTask((long) SkyPlusPlusClient.config.survivalAdInterval * TimeConsts.Second, TimeUnit.MILLISECONDS);
            }
            default -> {
                return;
            }
        }

    }
}
