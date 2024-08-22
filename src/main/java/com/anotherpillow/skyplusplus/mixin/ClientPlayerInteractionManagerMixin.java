package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import net.minecraft.block.GrassBlock;
import net.minecraft.item.Item;
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
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();

        if (stack.getItem().getTranslationKey().equals("block.minecraft.grass_block") && config.antiGrassPlace) {
            cb.setReturnValue(ActionResult.FAIL);
            cb.cancel();
        }

    }
}
