package com.anotherpillow.skyplusplus.features;

import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;

public class MessageAppend {
    public static String process(String message) {
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
        if (!config.chatPrefix && !config.chatSuffix) return message;

        if (config.chatPrefix && !message.startsWith("/")) {
            /* prepend it to the message. if the prefix starts with a / and the message doesn't start with a space, add one appropriately.*/
            message = (config.chatPrefixMessage.startsWith("/") && !message.startsWith(" ") ? config.chatPrefixMessage + " " : config.chatPrefixMessage) + message;
        }


        if (config.chatSuffix)
            message = message + config.chatSuffixMessage;


        return message;
    }
}
