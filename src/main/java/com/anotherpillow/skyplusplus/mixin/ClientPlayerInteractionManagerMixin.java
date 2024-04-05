package com.anotherpillow.skyplusplus.mixin;

import net.minecraft.item.SkullItem;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items.*;
@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    private void stopInteractBlock(ClientPlayerEntity player,
                                 Hand hand,
                                 BlockHitResult hit,
                                 CallbackInfoReturnable<ActionResult> cb) {

        ItemStack stack = player.getStackInHand(hand);

        if (stack.getItem() instanceof Grass)


    }
}
