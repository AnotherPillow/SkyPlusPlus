package com.anotherpillow.skyplusplus.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.time.Instant;
import java.time.ZoneId;

public class TraderCountdown {
    public static void draw(MatrixStack matrixStack, String txt, int textOffset) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        DrawableHelper.drawCenteredText(matrixStack, textRenderer, txt, 48 + textOffset,32, 0xFFFFFF);
    }
    public static String countdown() {
        //return minutes to the next UTC hour
        Instant now = Instant.now();

        //get minutes to the next hour
        int minutes = 60 - now.atZone(ZoneId.of("UTC")).getMinute();

        String minutesString = Integer.toString(minutes);
        //add leading zero if needed
        if (minutesString.length() == 1) minutesString = "0" + minutesString;

        if (minutesString.equals("60")) return "1:00";

        //convert to H:MM


        return "0:" + minutesString;
    }

    public static void TraderCountdown(MatrixStack matrixStack) {
        String txt = "Next trader in: " + countdown();
        int textWidth = MinecraftClient.getInstance().textRenderer.getWidth(txt);
        int textOffset = textWidth / 2;
        draw(matrixStack, txt, textOffset);
    }

}
