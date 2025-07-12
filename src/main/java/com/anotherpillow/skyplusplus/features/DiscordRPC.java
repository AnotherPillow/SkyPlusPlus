package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.anotherpillow.skyplusplus.util.Server;
//? if >1.19.2 {
import dev.isxander.yacl3.gui.YACLScreen;
//?} else {
/*import dev.isxander.yacl.gui.YACLScreen;
 *///?}
import meteordevelopment.discordipc.DiscordIPC;
import meteordevelopment.discordipc.RichPresence;
import net.minecraft.client.MinecraftClient;
//? if >=1.20.4 {
/*import net.minecraft.client.gui.screen.world.LevelLoadingScreen;
*///?} else {
import net.minecraft.client.gui.screen.LevelLoadingScreen;
 //?}

import net.minecraft.client.gui.screen.TitleScreen;

import java.time.Instant;

public class DiscordRPC {

    private static final RichPresence rpc = new RichPresence();
    public static final String largeText = "Sky++ %s".formatted(SkyPlusPlusClient.VERSION);

    private static MinecraftClient client = MinecraftClient.getInstance();

    public static void start() {


        if (!DiscordIPC.start(969647125027778589L,
                () -> System.out.println("Logged in account: " + DiscordIPC.getUser().username))) {
            System.out.println("Failed to start Discord IPC");
            return;
        }

        rpc.setStart(Instant.now().getEpochSecond());
        rpc.setLargeImage("logo512", largeText);
        rpc.setDetails("Sky++ " + SkyPlusPlusClient.VERSION);
        rpc.setState("Loading...");


        DiscordIPC.setActivity(rpc);

    }

    public static void onTick() {
        if (Server.getSkyblockMode() == Server.Mode.MOBARENA) rpc.setState("Playing in the Mob Arena");
        else if (Server.onSkyblock()) rpc.setState("Playing Skyblock");
        else if (client.currentScreen instanceof TitleScreen) rpc.setState("On the title screen");
        else if (client.currentScreen instanceof LevelLoadingScreen) rpc.setState("Loading...");
        else if (client.currentScreen instanceof YACLScreen) rpc.setState("Configuring");

        else rpc.setState("Playing Minecraft");

        DiscordIPC.setActivity(rpc);
    }
}
