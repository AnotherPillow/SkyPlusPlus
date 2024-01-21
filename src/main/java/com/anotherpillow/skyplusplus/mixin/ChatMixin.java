package com.anotherpillow.skyplusplus.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.network.ClientPlayerEntity;
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
import com.anotherpillow.skyplusplus.features.SmartTP;

@Mixin(ChatHud.class)
public class ChatMixin {
    private static MinecraftClient client = MinecraftClient.getInstance();
    private static final String username = client.getSession().getUsername().toLowerCase();
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

        if (config.hideAdvancementMessages
            && StringChecker.advancementMessageCheck(message))
                callback.cancel();

        if (config.hideBroadcastMessages
            && StringChecker.broadcastMessageCheck(message))
                callback.cancel();

        if (SmartTP.awaitingLock
            && StringChecker.TPAcceptCheck(message))
                SmartTP.lock();

        if (config.hideExpandMessages
            && StringChecker.expandMessageCheck(message))
                callback.cancel();

        if (config.hideLuckyCratesMessages
            && StringChecker.luckyCratesMessageCheck(message))
                callback.cancel();

        if (config.hideMailMessages
            && StringChecker.mailMessageCheck(message))
                callback.cancel();

        if (config.hideVoteMessages
            && StringChecker.voteMessageCheck(message))
                callback.cancel();

        if (config.autoRaffleEnabled && (
                StringChecker.playersOnlineJoinCheck(message) ||StringChecker.raffleWinCheck(message)
            ))
            client.player.sendCommand("raffle buy 5");







    }

}
