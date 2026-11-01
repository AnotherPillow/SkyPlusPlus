package com.anotherpillow.skyplusplus.screen;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
//? if <1.21.6 {
/*import com.mojang.blaze3d.systems.RenderSystem;
*///?}
//? if >=1.21.6 {
import net.minecraft.client.gl.RenderPipelines;
 //?}
//? if >=1.20.1 {
import net.minecraft.client.gui.DrawContext;
 //?} else {
/*import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
*///?}
import net.minecraft.util.Identifier;

public class TraderImage {

    private static final int SIZE = 32;

    private static final Identifier TEXTURE =
            //? if >=1.21 {
            Identifier.of("skyplusplus", "traderhead.png");
             //?} else {
            /*new Identifier("skyplusplus", "traderhead.png");
    *///?}

    public static void draw(
            //? if >=1.20.1 {
            DrawContext context
             //?} else {
            /*MatrixStack context
            *///?}
    ) {
        if (SkyPlusPlusClient.client.options.hudHidden) {
            return;
        }

        SkyPlusPlusConfig config =
                SkyPlusPlusConfig.configInstance.getConfig();

        // The older GUI APIs use global shader/blending state.
        //? if <1.21.6 {
        /*RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        *///?}

        //? if >=1.21.6 {
        context.drawTexture(
            RenderPipelines.GUI_TEXTURED,
            TEXTURE,
            config.traderX,
            config.traderY,
            0.0F,
            0.0F,
            SIZE,
            SIZE,
            SIZE,
            SIZE
        );
        //?} else if >=1.20.1 {
        /*context.drawTexture(
            TEXTURE,
            config.traderX,
            config.traderY,
            0.0F,
            0.0F,
            SIZE,
            SIZE,
            SIZE,
            SIZE
        );
        *///?} else {
        /*RenderSystem.setShaderTexture(0, TEXTURE);

        DrawableHelper.drawTexture(
                context,
                config.traderX,
                config.traderY,
                0.0F,
                0.0F,
                SIZE,
                SIZE,
                SIZE,
                SIZE
        );
        *///?}

        //? if <1.21.6 {
        /*RenderSystem.disableBlend();
        *///?}
    }
}