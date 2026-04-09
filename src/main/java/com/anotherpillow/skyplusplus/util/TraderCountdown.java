package com.anotherpillow.skyplusplus.util;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
//? if >=1.20.1 {
/*import net.minecraft.client.gui.DrawContext;
*///?} else {
import net.minecraft.client.gui.DrawableHelper;
 //?}
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;

import java.time.Instant;
import java.time.ZoneId;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import net.minecraft.text.Text;

public class TraderCountdown {

    public static void draw(/*? >=1.20.1 {*/ /*DrawContext ctx *//*?} else {*/MatrixStack matrixStack /*?}*/, String txt) {
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
        if (SkyPlusPlusClient.client.options.hudHidden) {
            return;
        }

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        float scale = Math.max(0.25f, config.traderSize / 32.0f);
        int textWidth = textRenderer.getWidth(txt);

        float left = config.traderX + config.traderSize + 2.0f;
        float centerY = config.traderY + (config.traderSize / 2.0f);

        int drawX = Math.round((left / scale) + (textWidth / 2.0f));
        int drawY = Math.round((centerY / scale) - (textRenderer.fontHeight / 2.0f));

        //? if >=1.21 {
        /*ctx.getMatrices().pushMatrix();
    ctx.getMatrices().scale(scale, scale);
    ctx.drawCenteredTextWithShadow(textRenderer, txt, drawX, drawY, 0xFFFFFF);
    ctx.getMatrices().popMatrix();
         *///?} else if >=1.20.1 {
        /*ctx.getMatrices().push();
    ctx.getMatrices().scale(scale, scale, 1.0f);
    ctx.drawCenteredTextWithShadow(textRenderer, txt, drawX, drawY, 0xFFFFFF);
    ctx.getMatrices().pop();
         *///?} else if >1.19.2 {
        /*matrixStack.push();
    matrixStack.scale(scale, scale, 1.0f);
    DrawableHelper.drawCenteredTextWithShadow(matrixStack, textRenderer, txt, drawX, drawY, 0xFFFFFF);
    matrixStack.pop();
         *///?} else {
        matrixStack.push();
        matrixStack.scale(scale, scale, 1.0f);
        DrawableHelper.drawCenteredText(matrixStack, textRenderer, txt, drawX, drawY, 0xFFFFFF);
        matrixStack.pop();
        //?}
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
        Server.Mode mode = Server.getSkyblockMode();

        if (validTime
                && SkyPlusPlusConfig.configInstance.getConfig().enableTraderTitles
                && !hasShownTitleForThisMinute
                && (mode == Server.Mode.ECONOMY || mode == Server.Mode.SURVIVAL)
        ) {

            Chat.sendTitle(Text.translatable("skyplusplus.trader.countdown.appeared"));
            hasShownTitleForThisMinute = true;
        } else if (!validTime && hasShownTitleForThisMinute) {
            hasShownTitleForThisMinute = false;
        }

        return "0:" + minutesString;
    }

    public static void DrawCountdown(/*? >=1.20.1 {*/ /*DrawContext ctx *//*?} else {*/MatrixStack matrixStack /*?}*/) {
        String txt = Text.translatable("skyplusplus.trader.countdown.timer").getString() + countdown();
        draw(/*? >=1.20.1 {*/ /*ctx *//*?} else {*/matrixStack /*?}*/, txt);
    }
}
