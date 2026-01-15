package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.anotherpillow.skyplusplus.util.Chat;
//? if >=1.21
/*import net.minecraft.client.gui.screen.ingame.AbstractSignEditScreen;*/
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.Objects;

@Mixin(SignEditScreen.class)
public class SignEditScreenMixin {
    //? if <1.21 {
    @Shadow @Final private String[] text;
    //?}
    @Unique
    private String[] initialText;

    @Inject(
            method="init()V",
            at=@At("TAIL")
    )
    private void init(CallbackInfo ci) {

        //? if >=1.21 {
        /*try {
            // should really fix the AW but reflection is fun ig
            Field field = ((AbstractSignEditScreen)(Object)(this)).getClass().getDeclaredField("text");
            field.setAccessible(true);

            this.initialText = ((String[])field.get(((AbstractSignEditScreen)(Object)(this)))).clone();
        } catch (Exception e) {
            this.initialText = new String[]{};
        }

        *///?} else {
        this.initialText = this.text.clone();
         //?}
    }

    //? if <1.21 {
    @Inject(
            method="finishEditing()V",
            at=@At("TAIL")
    )
    private void finishEditing(CallbackInfo ci) {
        if (!SkyPlusPlusClient.config.reEditPrivateSigns) return;

        String[] endText = this.text;

        for (int i = 1; i < 4; i++) { // can't modify top line
            if (!Objects.equals(initialText[i], endText[i])) {
                Chat.sendCommandToServer(String.format("blocklocker:blocklocker %d %s", i + 1,
                        endText[i].replaceAll(" ", "-")) ); // can't have spaces in it :(
            }
        }

    }
    //?}
}
