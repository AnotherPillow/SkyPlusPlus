package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.SkyPlusPlus;
import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.util.Chat;
import net.minecraft.text.Text;
import org.apache.logging.log4j.Level;


public class JoinCommands {
    public static void onServerJoin() {
        String[] commands = SkyPlusPlusClient.config.joinCommandsList.split(",");
        if (SkyPlusPlusClient.client.player == null) {
            SkyPlusPlus.log(Level.INFO, "Player is null, cannot send join message(s).");
            return;
        }
        Chat.send(Chat.addLogo(Text.translatable("skyplusplus.joincommands.sending-join-messages")));
        for (String command : commands) {
            if (command.trim().startsWith("/")) {
                SkyPlusPlus.log(Level.INFO, "Sending command: " + command.trim());
                SkyPlusPlusClient.client.player.sendCommand(command.trim().substring(1));
            }
            else {
                SkyPlusPlus.log(Level.INFO, "Sending chat: " + command);
                SkyPlusPlusClient.client.player.sendChatMessage(command, Text.of(""));
            }
        }

    }
}
