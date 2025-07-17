package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.features.Chatcryption;
import com.anotherpillow.skyplusplus.features.SlotLocker;
import com.anotherpillow.skyplusplus.util.Server;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
// was yarn 1.20.2-pre3 i think
//? if >=1.20.2 {
/*import net.minecraft.item.PlayerHeadItem;
*///?} else {
import net.minecraft.item.SkullItem;
 //?}

import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
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
        int hotbarSlot = self.getInventory().selectedSlot;
        int inventorySlotId = 27 + hotbarSlot;

        IntArrayList list = SlotLocker.lockedSlots.get(Server.getSkyblockMode());
        if (list == null) return;

        if (list.contains(inventorySlotId)) {
            cb.setReturnValue(false);
            return;
        }


        //? if >=1.20.2 {
        /*if (itemStack.getItem() instanceof PlayerHeadItem) {
        *///?} else {
        if (itemStack.getItem() instanceof SkullItem) {
         //?}
            if (config.preventHeadDropping) {
                client.inGameHud.getChatHud().addMessage(Text.of(String.valueOf(Chat.addLogo(Text.translatable("skyplusplus.preventheaddropping.prevented-drop", name)))));
                cb.setReturnValue(false);
            }
        }
    }

    @ModifyArg(
            method = "sendChatMessage(Ljava/lang/String;Lnet/minecraft/text/Text;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;sendChatMessageInternal(Ljava/lang/String;Lnet/minecraft/text/Text;)V"
            ),
            index = 0
    )
    private String sendChatMessage(String message) {
        // intercept chat messages NOT commands
        return message;
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
        // intercept commands without leading slash
        String[] _parts = command.split(" ");
        String name = _parts[0];
        String content = String.join(" ", Arrays.copyOfRange(_parts, 1, _parts.length));

        Chat.send("sending command: " + command);
        Chat.send("command as colours:" + Chatcryption.obfuscateBytesAsColour(command).replaceAll("&", "§"));
        if (Chatcryption.messageAliases.contains(name)) {
            String[] _cparts  = content.split(" ");
            String destname = _cparts[0];
            String mcontent = String.join(" ", Arrays.copyOfRange(_cparts, 1, _cparts.length));
            Chat.send("sending dm with content: [" + mcontent + "], does it start with start sign?" + mcontent.startsWith(Chatcryption.MESSAGE_START_SIGN));
            if (mcontent.startsWith(Chatcryption.MESSAGE_START_SIGN)) return command;

            Chatcryption.processOutgoingChatMessage(destname, mcontent);
            return "_sinkhole"; // delete arguments anyway, wip need to register this command
        }
        return command;
    }
}