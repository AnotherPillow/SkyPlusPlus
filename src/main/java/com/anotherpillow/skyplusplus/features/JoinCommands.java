package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.SkyPlusPlus;
import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.util.Chat;
import com.anotherpillow.skyplusplus.util.Server;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.text.Text;
import org.apache.logging.log4j.Level;


public class JoinCommands {
    public static void onServerJoin() {
        String[] commands = SkyPlusPlusClient.config.joinCommandsList.split(",");
        if (SkyPlusPlusClient.client.player == null) {
            SkyPlusPlus.log(Level.INFO, "Player is null, cannot send join message(s).");
            return;
        }

        if (Server.getSkyblockMode() != Server.Mode.ECONOMY &&
            Server.getSkyblockMode() != Server.Mode.CLASSIC &&
            Server.getSkyblockMode() != Server.Mode.SURVIVAL)
            return;
        if (!Server.onSkyblock()) return;

        Chat.send(Chat.addLogo(Text.translatable("skyplusplus.joincommands.sending-join-messages")));
        for (String command : commands) {
            if (command.trim().startsWith("/")) {
                SkyPlusPlus.log(Level.INFO, "Sending command: " + command.trim());
                //? if >1.19.2 {
                SkyPlusPlusClient.client.getNetworkHandler().sendChatCommand(command.trim().substring(1));
                //?} else if >1.19.2 && <1.21 {
                /*SkyPlusPlusClient.client.getNetworkHandler().sendCommand(command.trim().substring(1));
                *///?} else {
                /*SkyPlusPlusClient.client.player.sendCommand(command.trim().substring(1));
                 *///?}
            }
            else {
                SkyPlusPlus.log(Level.INFO, "Sending chat: " + command);
                //? if >1.19.2 {
                SkyPlusPlusClient.client.getNetworkHandler().sendChatMessage(command);
                //?} else {
                /*SkyPlusPlusClient.client.player.sendChatMessage(command, Text.of(""));
                 *///?}
            }
        }

    }
}
