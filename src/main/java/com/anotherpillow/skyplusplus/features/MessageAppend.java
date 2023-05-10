package com.anotherpillow.skyplusplus.features;

import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;

public class MessageAppend {
    public static ChatMessageC2SPacket process(ChatMessageC2SPacket packet) {
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
        if (!config.chatPrefix && !config.chatSuffix) return packet;

        String message = packet.chatMessage();

        if (config.chatPrefix)
            message = config.chatPrefixMessage + message;

        if (config.chatSuffix)
            message = message + config.chatSuffixMessage;


        return new ChatMessageC2SPacket(message, packet.timestamp(), packet.salt(), packet.signature(), packet.signedPreview(), packet.acknowledgment());
    }
}
