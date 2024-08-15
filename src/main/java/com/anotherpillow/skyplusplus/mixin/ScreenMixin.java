package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.keybinds.HoverNBTCopy;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class ScreenMixin {
    @Inject(method = "Lnet/minecraft/client/gui/screen/Screen;keyPressed(III)Z", at = @At("HEAD"), cancellable = true)
    public void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (keyCode == HoverNBTCopy.boundKeyCode) {
            HoverNBTCopy.logic(MinecraftClient.getInstance());
            cir.cancel();
        }
    }

}
