package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.keybinds.HoverNBTCopy;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin<T extends ScreenHandler> {
    @Shadow
    protected T handler;

    @Invoker("isPointOverSlot")
    protected abstract boolean invokeIsPointOverSlot(Slot slot, double pointX, double pointY);


    @Inject(method = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V", at = @At("HEAD"))
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        // https://www.reddit.com/r/fabricmc/comments/15drn3l/comment/ju95aqn
        for(int k = 0; k < this.handler.slots.size(); k++) {
            var slot = this.handler.slots.get(k);
            if (this.invokeIsPointOverSlot(slot, (double) mouseX, (double) mouseY)) {
                HoverNBTCopy.hoveredItem = slot.getStack();
            }
        }
    }
}
