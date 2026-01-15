package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.stream.IntStream;

@Mixin(AbstractSignBlock.class)
public abstract class AbstractSignBlockMixin {
    @Shadow public abstract BlockEntity createBlockEntity(BlockPos pos, BlockState state);

    @Inject(
            method = "onUse",
            at=@At("HEAD")
    )
    //? if <1.21 {
    private void onUse(BlockState state, World world, BlockPos pos,
           PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
    //?} else {
    /*private void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
    *///?}
        if (!world.isClient) return;
        if (!SkyPlusPlusClient.config.reEditPrivateSigns) return;

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof SignBlockEntity signBE)) {
            return;
        }


        //? if >=1.21 {
        /*String firstLine = signBE.getText(true).getMessage(0, false).getString();
        *///?} else {
        String firstLine = signBE.getTextOnRow(0, false).getString();
         //?}
        // /blocklocker:blocklocker only works for locked signs :(
        if (Objects.equals(firstLine, "[Private]") || Objects.equals(firstLine, "[More Users]")
            || Objects.equals(firstLine, "[Everyone]")) {
            SignEditScreen ns = new SignEditScreen(signBE, /*? >=1.20.1 {*//*true, *//*?}*/false);

            SkyPlusPlusClient.client.setScreen(ns);
        }
    }
}
