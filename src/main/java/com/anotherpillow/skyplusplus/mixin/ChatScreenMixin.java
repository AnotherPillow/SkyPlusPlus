package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @ModifyArg(method={"keyPressed"},
            at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/screen/ChatScreen;sendMessage(Ljava/lang/String;Z)Z"), index=0)
    private String result(String text) {
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
        if (text.startsWith("/") && config.lowerCommandsEnabled) {
            String command = text.split(" ")[0];
            text = text.replace(command, command.toLowerCase());
        }
        return text;
    }
}
