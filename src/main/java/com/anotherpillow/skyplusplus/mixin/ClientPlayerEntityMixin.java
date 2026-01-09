package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.features.Chatcryption;
import com.anotherpillow.skyplusplus.features.SlotLocker;
import com.anotherpillow.skyplusplus.util.Server;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.client.MinecraftClient;
import com.anotherpillow.skyplusplus.util.MixinCommon;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
// was yarn 1.20.2-pre3 i think
//? if >=1.20.2 {
import net.minecraft.item.PlayerHeadItem;
//?} else {
/*import net.minecraft.item.SkullItem;
 *///?}

import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.util.Chat;

import java.util.Arrays;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "dropSelectedItem(Z)Z", at = @At(value = "HEAD"), cancellable = true)
    private void stopDropSelectedItem(boolean entireStack, CallbackInfoReturnable<Boolean> cb) {
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
        ClientPlayerEntity self = (ClientPlayerEntity) (Object) this;
        ItemStack itemStack = self.getMainHandStack();
        MinecraftClient client = MinecraftClient.getInstance();

        String name = itemStack.getName().getString();
        //? if >=1.21 {
        int hotbarSlot = self.getInventory().getSelectedSlot();
        //?} else {
        /*int hotbarSlot = self.getInventory().selectedSlot;
         *///?}
        int inventorySlotId = 27 + hotbarSlot;

        IntArrayList list = SlotLocker.lockedSlots.get(Server.getSkyblockMode());
        if (list == null) return;

        if (list.contains(inventorySlotId)) {
            cb.setReturnValue(false);
            return;
        }


        //? if >=1.20.2 {
        if (itemStack.getItem() instanceof PlayerHeadItem) {
            //?} else {
            /*if (itemStack.getItem() instanceof SkullItem) {
             *///?}
            if (config.preventHeadDropping) {
                client.inGameHud.getChatHud().addMessage(Text.of(String.valueOf(Chat.addLogo(Text.translatable("skyplusplus.preventheaddropping.prevented-drop", name)))));
                cb.setReturnValue(false);
            }
        }
    }


    //? if >1.19.2 {
    //?} else {
    


    /*@ModifyArg(
        method = "sendChatMessage(Ljava/lang/String;Lnet/minecraft/text/Text;)V",
        at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/client/network/ClientPlayerEntity;sendChatMessageInternal(Ljava/lang/String;Lnet/minecraft/text/Text;)V"
        ),
        index = 0
    )
    private String sendChatMessage(String message) {
        return MixinCommon.genericChatMixinMethod(message);
    }
    @ModifyArg(
            method = "sendCommand(Ljava/lang/String;Lnet/minecraft/text/Text;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;sendCommandInternal(Ljava/lang/String;Lnet/minecraft/text/Text;)V"
            ),
            index = 0
    )
    private String sendCommand(String command) {
        return MixinCommon.genericCommandMixinMethod(command);
    }

    
    *///?}
}