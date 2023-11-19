package com.anotherpillow.skyplusplus.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.time.Instant;
import java.time.ZoneId;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;

public class TraderCountdown {
    public static void draw(MatrixStack matrixStack, String txt, int textOffset) {
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        DrawableHelper.drawCenteredText(matrixStack, textRenderer, txt, config.traderX + 32 + textOffset,config.traderY + 16, 0xFFFFFF);
    }
    public static String countdown() {
        Instant now = Instant.now();

        int minutes = 60 - now.atZone(ZoneId.of("UTC")).getMinute();

        String minutesString = Integer.toString(minutes);

        if (minutesString.length() == 1) minutesString = "0" + minutesString;
        if (minutesString.equals("60")) return "1:00";

        return "0:" + minutesString;
    }

    public static void TraderCountdown(MatrixStack matrixStack) {
        String txt = "Next trader in: " + countdown();
        int textWidth = MinecraftClient.getInstance().textRenderer.getWidth(txt);
        int textOffset = textWidth / 2;
        draw(matrixStack, txt, textOffset);
    }
}
