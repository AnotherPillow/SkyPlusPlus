package com.anotherpillow.skyplusplus.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

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

}
