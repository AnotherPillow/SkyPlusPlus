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
                Chat.sendCommandToServer(command.trim().substring(1));
            }
            else {
                SkyPlusPlus.log(Level.INFO, "Sending chat: " + command);
                Chat.sendChatToServer(command);
            }
        }

    }
}
