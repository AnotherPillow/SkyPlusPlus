package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.keybinds.HoverNBTCopy;
import com.anotherpillow.skyplusplus.keybinds.LockSlotBind;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class ScreenMixin {
    @Inject(method = "keyPressed(III)Z", at = @At("HEAD"), cancellable = true)
    public void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
//        MinecraftClient.getInstance().player.sendMessage(Text.literal(String.format("HBNTBKC: %d / KC: %d, SC: %d, KEYC: %d, KEYR: %d",
//                HoverNBTCopy.boundKeyCode, keyCode, scanCode, GLFW.GLFW_KEY_C, GLFW.GLFW_KEY_R)), false);

        InputUtil.Key hoverNbtKey = KeyBindingHelper.getBoundKeyOf(HoverNBTCopy.binding);
        InputUtil.Key lockslotKey = KeyBindingHelper.getBoundKeyOf(LockSlotBind.binding);

        if (keyCode == hoverNbtKey.getCode()) {
            HoverNBTCopy.logic(MinecraftClient.getInstance());
            cir.cancel();
        }
    }

}
