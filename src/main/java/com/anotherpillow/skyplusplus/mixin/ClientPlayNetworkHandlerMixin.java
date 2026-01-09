package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.util.MixinCommon;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    //? if >=1.19.4 {
    @ModifyVariable(
            method="sendChatCommand(Ljava/lang/String;)V",
            at=@At("HEAD"),
            ordinal = 0
    )
    private String sendChatCommand(String command) {
        return MixinCommon.genericCommandMixinMethod(command);
    }
    // idk what the difference is
    @ModifyVariable(
            //? if >= 1.21 {
            method="sendChatCommand",
            //?} else {
            /*method= "sendCommand(Ljava/lang/String;)Z",
            *///?}
            at=@At("HEAD"),
            ordinal = 0
    )
    private String sendCommand(String command) {
        return MixinCommon.genericCommandMixinMethod(command);
    }

    @ModifyVariable(
            method= "sendChatMessage(Ljava/lang/String;)V",
            at=@At("HEAD"),
            ordinal = 0
    )
    private String sendChatMessage(String message) {
        return MixinCommon.genericChatMixinMethod(message);
    }

    //?}
}
