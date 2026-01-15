package com.anotherpillow.skyplusplus.screen;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
//? if >=1.21 {
/*import com.mojang.blaze3d.vertex.VertexFormat;
import org.joml.Matrix3x2f;
*///?} else {
import net.minecraft.client.render.VertexFormat;
 //?}
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
//? if >1.19.2 {
/*import org.joml.Matrix4f;
*///?} else {
import net.minecraft.util.math.Matrix4f;
 //?}
import net.minecraft.client.render.GameRenderer;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;

public class TraderImage {

    public static void draw(/*? >=1.21 {*/ /*Matrix3x2f matrixStack *//*?} else {*/ MatrixStack matrixStack /*?}*/) {
        // not draw in f1
        if (SkyPlusPlusClient.client.options.hudHidden) return;

        //? if >=1.21 {
        /*Matrix4f positionMatrix = new Matrix4f();
//        float[] points = {};
//        positionMatrix.set(matrixStack.get(points));
        boolean b = true;
        if (b) return;
        *///?} else {
        Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();
         //?}



        Tessellator tessellator = Tessellator.getInstance();


        //? <1.21
         BufferBuilder buffer = tessellator.getBuffer();

        //? >=1.21
        /*BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);*/

        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();

        //? if >=1.21 {
        /*buffer.vertex(positionMatrix, config.traderX, config.traderY, 0).color(1f, 1f, 1f, 1f).texture(0f, 0f);
        buffer.vertex(positionMatrix, config.traderX, config.traderY + 32, 0).color(1f, 1f, 1f, 1f).texture(0f, 1f);
        buffer.vertex(positionMatrix, config.traderX + 32, config.traderY + 32, 0).color(1f, 1f, 1f, 1f).texture(1f, 1f);
        buffer.vertex(positionMatrix, config.traderX + 32, config.traderY, 0).color(1f, 1f, 1f, 1f).texture(1f, 0f);
        *///?} else {
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        buffer.vertex(positionMatrix, config.traderX, config.traderY, 0).color(1f, 1f, 1f, 1f).texture(0f, 0f).next();
        buffer.vertex(positionMatrix, config.traderX, config.traderY + 32, 0).color(1f, 1f, 1f, 1f).texture(0f, 1f).next();
        buffer.vertex(positionMatrix, config.traderX + 32, config.traderY + 32, 0).color(1f, 1f, 1f, 1f).texture(1f, 1f).next();
        buffer.vertex(positionMatrix, config.traderX + 32, config.traderY, 0).color(1f, 1f, 1f, 1f).texture(1f, 0f).next();
         //?}


        //? if >1.19.2 {

        //?} else {
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
         //?}


        //? if >1.19.2 {

        //?} else {
        RenderSystem.setShaderTexture(0, new Identifier("skyplusplus", "traderhead.png"));
         //?}
        //? <1.21
         RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        //? if >=1.21 {
        /*buffer.end();
        *///?} else {
        tessellator.draw();
         //?}


    }
}