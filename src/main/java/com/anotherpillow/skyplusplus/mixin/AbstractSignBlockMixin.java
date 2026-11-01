package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(AbstractSignBlock.class)
public abstract class AbstractSignBlockMixin {

    @Unique
    private static void skyplusplus$tryOpen(World world, BlockPos pos) {
        if (!world.isClient) return;
        if (!SkyPlusPlusClient.config.reEditPrivateSigns) return;
        if (SkyPlusPlusClient.client.currentScreen instanceof SignEditScreen) return;

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof SignBlockEntity signBE)) return;

        //? if >=1.20.1 {
        String firstLine = signBE.getText(true).getMessage(0, false).getString();
         //?} else {
        /*String firstLine = signBE.getTextOnRow(0, false).getString();
        *///?}

        if (Objects.equals(firstLine, "[Private]")
                || Objects.equals(firstLine, "[More Users]")
                || Objects.equals(firstLine, "[Everyone]")) {
            SignEditScreen ns = new SignEditScreen(signBE, /*? >=1.20.1 {*/true, /*?}*/false);
            SkyPlusPlusClient.client.setScreen(ns);
        }
    }

    @Inject(method = "onUse", at = @At("HEAD"))
            //? if <1.21 {
    /*private void skyplusplus$onUse(BlockState state, World world, BlockPos pos,
                                   PlayerEntity player, Hand hand, BlockHitResult hit,
                                   CallbackInfoReturnable<ActionResult> cir) {
        *///?} else {
    private void skyplusplus$onUse(BlockState state, World world, BlockPos pos,
            PlayerEntity player, BlockHitResult hit,
            CallbackInfoReturnable<ActionResult> cir) {
    //?}
        skyplusplus$tryOpen(world, pos);
    }

    //? if >=1.21.2 {
    @Inject(method = "onUseWithItem", at = @At("HEAD"))
    private void skyplusplus$onUseWithItem(ItemStack stack, BlockState state,
            World world, BlockPos pos, PlayerEntity player, Hand hand,
            BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        skyplusplus$tryOpen(world, pos);
    }
    //?}
}