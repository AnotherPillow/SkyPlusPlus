package com.anotherpillow.skyplusplus.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SkullItem;
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

        System.out.println("item: " + itemStack.getName());
        if (itemStack.getItem() instanceof SkullItem) {
            System.out.println("Head was dropped");
            if (config.preventHeadDropping) {
                System.out.println("Dropping cancelled");
                client.inGameHud.getChatHud().addMessage(Text.of(Chat.addLogo("Prevented dropping " + name)));
                cb.setReturnValue(false);
            }
        }
    }
}