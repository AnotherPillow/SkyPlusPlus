package com.anotherpillow.skyplusplus.mixin;
//? if 1.20.1
/*import net.minecraft.block.entity.SignText;*/
//? if 1.20.1
/*import net.minecraft.client.gui.screen.ingame.AbstractSignEditScreen;*/
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

//? if 1.20.1
/*@Mixin(AbstractSignEditScreen.class)*/
//? if !=1.20.1
@Mixin(SignEditScreen.class) // can't have it break since no stonecutter in mixins file
//? if 1.20.1
/*public interface AbstractSignEditScreenAccessor {*/
//? if !=1.20.1
public class AbstractSignEditScreenAccessor {
    //? if 1.20.1 {
    /*@Accessor("messages")
    String[] skyplusplus$getMessages();


    @Accessor("messages")
    void skyplusplus$setMessages(String[] value);
    *///?}
}