package com.anotherpillow.skyplusplus.keybinds;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class HoverNBTCopy {

    public static KeyBinding binding;

    public static void register() {
        binding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.examplemod.spook", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_C, // The keycode of the key
                "category.examplemod.test" // The translation key of the keybinding's category.
        ));
    }

    public static void logic(MinecraftClient client) {

        if (!(client.currentScreen instanceof InventoryScreen)) return;
        client.player.sendMessage(Text.literal("Key was pressed!"), false);
    }
}
