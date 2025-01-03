package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.features.*;
import com.anotherpillow.skyplusplus.util.Chat;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.network.message.MessageSignatureData;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.util.math.Vec3d;
import java.io.IOException;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.anotherpillow.skyplusplus.util.StringChecker;

@Mixin(ChatHud.class)
public abstract class ChatMixin {
    @Shadow protected abstract void addMessage(Text message, @Nullable MessageSignatureData signature, int ticks, @Nullable MessageIndicator indicator, boolean refresh);
    private static MinecraftClient mc = MinecraftClient.getInstance(); // Why did I need to rename this? I genuinely have no idea. Apparently there's a non-static client field in the original class but that wasn't always an issue, so I have no idea.
    private static final String username = mc.getSession().getUsername().toLowerCase();
    @Inject(
            method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void skyplusplus$onChatReceived(Text text_message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh, CallbackInfo callback) throws IOException {
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
//        System.out.println(Text.Serializer.toJson(text_message));
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
            mc.player.sendCommand("raffle buy 5");

        if (message.startsWith("You last logged in ")) {
            AutoAdvertisement.onServerJoin();
            JoinCommands.onServerJoin();
        }
    }

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("HEAD"), cancellable = true)
    public void addMessageMixin(Text message, @Nullable MessageSignatureData signature, @Nullable MessageIndicator indicator, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (SkyPlusPlusClient.config.removeChatRanks) {
            addMessage(RemoveChatRanks.process(message), signature, client.inGameHud.getTicks(), indicator, false);
            ci.cancel();
        }

    }
}
