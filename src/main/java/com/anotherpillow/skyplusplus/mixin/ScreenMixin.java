package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.keybinds.HoverNBTCopy;
import com.anotherpillow.skyplusplus.util.Chat;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow @Nullable protected MinecraftClient client;

    @Shadow public abstract void renderBackgroundTexture(int vOffset);

    @Shadow public int width;

    @Shadow public int height;

    protected void fillGradient(MatrixStack matrices, int startX, int startY, int endX, int endY, int colorStart, int colorEnd) {}

    @Inject(method = "Lnet/minecraft/client/gui/screen/Screen;keyPressed(III)Z", at = @At("HEAD"), cancellable = true)
    public void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
//        MinecraftClient.getInstance().player.sendMessage(Text.literal(String.format("HBNTBKC: %d / KC: %d, SC: %d, KEYC: %d, KEYR: %d",
//                HoverNBTCopy.boundKeyCode, keyCode, scanCode, GLFW.GLFW_KEY_C, GLFW.GLFW_KEY_R)), false);

        InputUtil.Key desiredKey = KeyBindingHelper.getBoundKeyOf(HoverNBTCopy.binding);

//        if (keyCode == HoverNBTCopy.boundKeyCode) {
        if (keyCode == desiredKey.getCode()) {
            HoverNBTCopy.logic(MinecraftClient.getInstance());
            cir.cancel();
        }
    }

    @Inject(method = "Lnet/minecraft/client/gui/screen/Screen;renderBackground(Lnet/minecraft/client/util/math/MatrixStack;I)V", at=@At("HEAD"), cancellable = true)
    public void renderBackground(MatrixStack matrices, int vOffset, CallbackInfo ci) {
        if (this.client == null && SkyPlusPlusClient.client != null) {
//            this.renderBackgroundTexture(15);
            this.client = MinecraftClient.getInstance();
            this.renderBackgroundTexture(15);
//            ci.cancel();
        }
    }

}
