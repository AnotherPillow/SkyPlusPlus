package com.anotherpillow.skyplusplus.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.network.message.MessageSignatureData;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.util.math.Vec3d;
import java.io.IOException;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.anotherpillow.skyplusplus.util.StringChecker;
import com.anotherpillow.skyplusplus.features.AutoResponder;

@Mixin(ChatHud.class)
public class ChatMixin {
    private static final String username = MinecraftClient.getInstance().getSession().getUsername().toLowerCase();
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At("HEAD"), cancellable = true)
    private void skyplusplus$onChatReceived(Text text_message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh, CallbackInfo callback) throws IOException {
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
        String message = text_message.getString();

        if (config.hideVisitingMessages
            && StringChecker.welcomeIslandCheck(message))
                callback.cancel();

        if (config.hideSkyblockMessages
            && StringChecker.skyblockMessageCheck(message))
                callback.cancel();

        if (config.hideUnscramblingMessages
            && message.startsWith("[✎]"))
                callback.cancel();

        if (config.hideAFKMessages
            && StringChecker.AFKMessageCheck(message))
                callback.cancel();

        if (config.hideNewUserMessages
            && StringChecker.newUserMessageCheck(message))
                callback.cancel();

        if (config.hideDeathsMessages
            && StringChecker.deathMessageCheck(message))
                callback.cancel();

        if (config.hideRaffleMessages
            && StringChecker.raffleMessageCheck(message))
                callback.cancel();

        if (config.enableAutoResponder
            && StringChecker.autoResponderCheck(message))
                AutoResponder.respond();


    }

}
