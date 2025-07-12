package com.anotherpillow.skyplusplus.mixin;

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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.anotherpillow.skyplusplus.util.Chat;
@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "dropSelectedItem(Z)Z", at = @At(value = "HEAD"), cancellable = true)
    private void stopDropSelectedItem(boolean entireStack, CallbackInfoReturnable<Boolean> cb) {
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
        ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) (Object) this;
        ItemStack itemStack = clientPlayerEntity.getMainHandStack();
        MinecraftClient client = MinecraftClient.getInstance();

        String name = itemStack.getName().getString();

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
}