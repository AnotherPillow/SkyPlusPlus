package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.util.Chat;
//? if >=1.20 {
import net.minecraft.client.gui.screen.ingame.AbstractSignEditScreen;
 //?} else {
/*import net.minecraft.client.gui.screen.ingame.SignEditScreen;
*///?}
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.Objects;

//? if >=1.20 {
@Mixin(AbstractSignEditScreen.class)
public abstract class SignEditScreenMixin {
    @Shadow @Final private String[] messages;
//?} else {
/*@Mixin(SignEditScreen.class)
public class SignEditScreenMixin {
    @Shadow @Final private String[] text;
*///?}

    @Unique
    private String[] skyplusplus$initialText;

    @Unique
    private String[] skyplusplus$currentLines() {
        //? if >=1.20 {
        return this.messages;
         //?} else {
        /*return this.text;
        *///?}
    }

    @Inject(method = "init()V", at = @At("TAIL"))
    private void skyplusplus$init(CallbackInfo ci) {
        if (this.skyplusplus$initialText != null) return;
        String[] lines = this.skyplusplus$currentLines();
        this.skyplusplus$initialText = lines == null
                ? new String[0]
                : Arrays.copyOf(lines, lines.length);
    }

    @Unique
    private void skyplusplus$sendChanges() {
        if (!SkyPlusPlusClient.config.reEditPrivateSigns) return;

        String[] endText = this.skyplusplus$currentLines();
        if (this.skyplusplus$initialText == null || endText == null) return;
        if (this.skyplusplus$initialText.length < 4 || endText.length < 4) return;

        for (int i = 1; i < 4; i++) {
            if (!Objects.equals(this.skyplusplus$initialText[i], endText[i])) {
                Chat.sendCommandToServer(String.format(
                        "blocklocker:blocklocker %d %s",
                        i + 1,
                        endText[i].replace(" ", "-")));
            }
        }
    }

    //? if >=1.20 {
    @Inject(method = "removed()V", at = @At("HEAD"))
    private void skyplusplus$removed(CallbackInfo ci) {
        this.skyplusplus$sendChanges();
    }
    //?} else {
    /*@Inject(method = "finishEditing()V", at = @At("TAIL"))
    private void skyplusplus$finish(CallbackInfo ci) {
        this.skyplusplus$sendChanges();
    }
    *///?}
}