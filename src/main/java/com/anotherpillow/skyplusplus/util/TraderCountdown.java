package com.anotherpillow.skyplusplus.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.time.Instant;
import java.time.ZoneId;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import net.minecraft.text.Text;

public class TraderCountdown {
    public static void draw(MatrixStack matrixStack, String txt, int textOffset) {
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        DrawableHelper.drawCenteredText(matrixStack, textRenderer, txt, config.traderX + 32 + textOffset,config.traderY + 16, 0xFFFFFF);
    }

    private static boolean hasShownTitleForThisMinute = false;
    public static String countdown() {
        // Instant now = Instant.parse("2023-12-27T16:00:00.00Z");
        Instant now = Instant.now();
        int mins = now.atZone(ZoneId.of("UTC")).getMinute();
        int minutes = 60 - (mins == 0 ? 60 : mins);

        String minutesString = Integer.toString(minutes);

        if (minutesString.length() == 1) minutesString = "0" + minutesString;
        if (minutesString.equals("60")) return "1:00";

        boolean validTime = (minutesString.equals("00") || minutesString.equals("60"));

        if (validTime
                && SkyPlusPlusConfig.configInstance.getConfig().enableTraderTitles && !hasShownTitleForThisMinute) {

            Chat.sendTitle(Text.translatable("skyplusplus.trader.countdown.appeared"));
            hasShownTitleForThisMinute = true;
        } else if (!validTime && hasShownTitleForThisMinute) {
            hasShownTitleForThisMinute = false;
        }

        return "0:" + minutesString;
    }

    public static void TraderCountdown(MatrixStack matrixStack) {
        String txt = String.valueOf(Text.translatable("skyplusplus.trader.countdown.timer", countdown()));
        int textWidth = MinecraftClient.getInstance().textRenderer.getWidth(txt);
        int textOffset = textWidth / 2;
        draw(matrixStack, txt, textOffset);
    }
}
