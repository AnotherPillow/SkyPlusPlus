package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.SkyPlusPlus;
import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.anotherpillow.skyplusplus.features.SlotLocker;
import com.anotherpillow.skyplusplus.util.Chat;
import com.anotherpillow.skyplusplus.util.Server;
import com.anotherpillow.skyplusplus.util.StringChecker;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
//? if >=1.20.1 {
/*import net.minecraft.client.gui.DrawContext;
*///?} else {
import net.minecraft.client.gui.DrawableHelper;
 //?}
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    //? if >=1.20.4 {

    //?} else {
    @Shadow
    private static final Identifier WIDGETS_TEXTURE = new Identifier("textures/gui/widgets.png");
     //?}

    @Shadow
    private int scaledHeight;

    @Inject(
            at=@At("HEAD"),
            method="Lnet/minecraft/client/gui/hud/InGameHud;setTitle(Lnet/minecraft/text/Text;)V",
            cancellable = true
    )
    private void setTitle(Text title, CallbackInfo ci) {
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
        if (StringChecker.visitingTitleCheck(title.getString()) && config.hideVisitingTitle) ci.cancel();
    }

    // inject between drawing the hotbar and drawing the selected overlay
    //? if >=1.20.1 {
    /*@Inject(
            method = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbar(FLnet/minecraft/client/gui/DrawContext;)V",
            at = @At(
                    value = "INVOKE",
                    //? if >=1.20.4 {
                    /^target="Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
                    ^///?} else {
                    target="Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V",
                     //?}

                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void renderHotbar(float tickDelta, DrawContext context, CallbackInfo ci, PlayerEntity playerEntity, ItemStack itemStack, Arm arm, int i, int j, int k) {
    *///?} else {
    @Inject(
            method = "renderHotbar(FLnet/minecraft/client/util/math/MatrixStack;)V",
            at = @At(
                    value = "INVOKE",
                    target="Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V",
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void renderHotbar(float tickDelta, MatrixStack matrices, CallbackInfo ci, PlayerEntity playerEntity, ItemStack itemStack, Arm arm, int i, int j) {
     //?}

        IntArrayList lockedSlots = SlotLocker.lockedSlots.get(Server.getSkyblockMode());
//        Chat.send("lockSlots: " + lockedSlots);
        if (lockedSlots == null) return;

        //? if >=1.20.1 {

        //?} else {
        RenderSystem.setShaderTexture(0, SkyPlusPlusClient.lockId);
         //?}

        for (int slotOffset = 0; slotOffset < 9; slotOffset++) {
            if (!lockedSlots.contains(27 + slotOffset)) continue;

            int _x = (i - 88) + (slotOffset * 20);
            int _y = this.scaledHeight - 19;

            //? if >=1.20.1 {
            /*context.drawTexture(
                    SkyPlusPlusClient.lockId,
                    _x,
                    _y,
                    0f,
                    0f,
                    16,
                    16,
                    16,
                    16
            );
            *///?} else {
            DrawableHelper.drawTexture(
                    matrices,
                    _x,
                    _y,
                    0f,
                    0f,
                    16,
                    16,
                    16,
                    16
            );
             //?}

        };
        // revert to old texture
        //? if >=1.20.4 {

        //?} else {
        RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
         //?}

    }
}
