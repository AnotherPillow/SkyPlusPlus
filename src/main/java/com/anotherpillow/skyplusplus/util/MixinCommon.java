package com.anotherpillow.skyplusplus.util;

public class MixinCommon {
    public static String genericCommandMixinMethod(String command) {
//        SkyPlusPlusClient.LOG.info("Intercepted sending command: {}", command);

        return command;
    }

        public static String genericChatMixinMethod(String message) {
        return message;
    }
}
