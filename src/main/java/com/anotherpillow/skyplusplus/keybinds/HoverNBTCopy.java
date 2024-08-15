package com.anotherpillow.skyplusplus.keybinds;

import com.anotherpillow.skyplusplus.util.Chat;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class HoverNBTCopy {

    public static KeyBinding binding;
    public static ItemStack hoveredItem = null;

    public static void register() {
        binding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.examplemod.spook", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_C, // The keycode of the key
                "category.examplemod.test" // The translation key of the keybinding's category.
        ));
    }

    public static void logic(MinecraftClient client) {
        if (!(client.currentScreen instanceof HandledScreen)) return;
//        client.player.sendMessage(Text.literal("Key was pressed! hovered item: " + hoveredItem.getItem().getName().toString()), false);


        MutableText text = Chat.addLogo(Text.empty());

        var nbt = hoveredItem.getNbt();
        if (nbt != null) text.append(NbtHelper.toPrettyPrintedText(nbt));
        else text.append("{}");

        Chat.send(text);
    }
}
