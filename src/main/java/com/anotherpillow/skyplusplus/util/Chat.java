package com.anotherpillow.skyplusplus.util;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import net.minecraft.client.MinecraftClient;
//? if >1.19.2 {
/*import net.minecraft.client.network.ClientPlayNetworkHandler;
*///?} else {
//?}
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
//? if >1.19.2 {
/*import net.minecraft.client.network.ClientPlayNetworkHandler;
*///?} else {

//?}

public class Chat {
    public static String logo = "§8§l[§b§lSky§3§l++§8§l]§r ";

    public static MinecraftClient client = MinecraftClient.getInstance();

    public static String addLogo(String input) {
        return logo + input;
    }
    public static MutableText addLogo(Text input) { return Text.empty().append(Text.of(logo)).append(input); }

    public static void send(String text) {
        client.inGameHud.getChatHud().addMessage(Text.of(text));
    }

    public static void send(Text text) {
        client.inGameHud.getChatHud().addMessage(text);
    }
    public static void sendTitle(String text) {
        _sendTitle(text, 5, 300, 5);
    }
    public static void _sendTitle(String text, int fadein, int stay, int fadeout) {
        client.inGameHud.setTitleTicks(fadein, stay, fadeout);
        client.inGameHud.setTitle(Text.of(text));
    }
    public static void sendTitle(Text text) {
        _sendTitle(text, 5, 300, 5);
    }
    public static void _sendTitle(Text text, int fadein, int stay, int fadeout) {
        client.inGameHud.setTitleTicks(fadein, stay, fadeout);
        client.inGameHud.setTitle(text);
    }

    // e.g. sendCommandToServer("tpahere Noobcrew")
    public static void sendCommandToServer(String noSlashCommandWithAllArguments) {
        //? if >=1.21 {
        /*ClientPlayNetworkHandler handler = client.getNetworkHandler();
        if (handler == null) return;
        handler.sendChatCommand(noSlashCommandWithAllArguments);
        *///?} else if >1.19.2 && <1.21 {
        /*ClientPlayNetworkHandler handler = client.getNetworkHandler();
        if (handler == null) return;
        handler.sendCommand(noSlashCommandWithAllArguments);
        *///?} else {
        ClientPlayerEntity player = client.player;
        if (player == null) return;
        player.sendCommand(noSlashCommandWithAllArguments, Text.empty());
         //?}
    }

}
