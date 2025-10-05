package com.anotherpillow.skyplusplus.util;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.features.Chatcryption;

import java.util.Arrays;

public class MixinCommon {
    public static String genericCommandMixinMethod(String command) {
        SkyPlusPlusClient.LOG.info("Intercepted sending command: {}", command);
        // intercept commands without leading slash
        String[] _parts = command.split(" ");
        String name = _parts[0];
        String content = String.join(" ", Arrays.copyOfRange(_parts, 1, _parts.length));

        // Chat.send("sending command: " + command);
        // Chat.send("command as colours:" + Chatcryption.obfuscateBytesAsColour(command).replaceAll("&", "§"));

        if (!SkyPlusPlusClient.config.chatcryptionEnabled) return command;

        if (Chatcryption.messageAliases.contains(name)) {
            String[] _cparts = content.split(" ");
            String destname = _cparts[0];
            String mcontent = String.join(" ", Arrays.copyOfRange(_cparts, 1, _cparts.length));
            // Chat.send("sending dm with content: [" + mcontent + "], does it start with start sign?" + mcontent.startsWith(Chatcryption.MESSAGE_START_SIGN));
            if (mcontent.startsWith(Chatcryption.MESSAGE_START_SIGN)) return command;

            Chatcryption.processOutgoingChatMessage(destname, mcontent);
            return "sky++_sinkhole";
        }
        if (Chatcryption.replyAliases.contains(name)) {
            String[] _cparts = content.split(" ");

            if (Chatcryption.lastCommunicatedUser == null) return command;

            String mcontent = String.join(" ", Arrays.copyOfRange(_cparts, 1, _cparts.length));
            // Chat.send("sending dm with content: [" + mcontent + "], does it start with start sign?" + mcontent.startsWith(Chatcryption.MESSAGE_START_SIGN));
            if (mcontent.startsWith(Chatcryption.MESSAGE_START_SIGN)) return command;

            Chatcryption.processOutgoingChatMessage(Chatcryption.lastCommunicatedUser, mcontent);
            return "sky++_sinkhole";
        }


        return command;
    }

        public static String genericChatMixinMethod(String message) {
        return message;
    }
}
