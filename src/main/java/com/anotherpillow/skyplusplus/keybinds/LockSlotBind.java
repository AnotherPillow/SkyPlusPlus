package com.anotherpillow.skyplusplus.keybinds;

import com.anotherpillow.skyplusplus.features.SlotLocker;
import com.anotherpillow.skyplusplus.util.Chat;
import com.anotherpillow.skyplusplus.util.Server;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;

public class LockSlotBind {
    public static KeyBinding binding;

    public static void register() {
        binding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "skyplusplus.keybind.lockslot", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_PAGE_DOWN, // The keycode of the key
                "Sky++" // The translation key of the keybinding's category.
        ));
    }

    public static void logic(MinecraftClient client) {
        Server.Mode mode = Server.getSkyblockMode();
        IntArrayList list = SlotLocker.lockedSlots.get(mode);
        HandledScreen<?> currentScreen = (HandledScreen<?>)client.currentScreen;
        ScreenHandler handler = currentScreen.getScreenHandler();

        int playerInvStart = handler.slots.size() - 37;
        int indexInInventory = SlotLocker.hoveredSlot.id - playerInvStart;

        if (0 > indexInInventory) return;

        if (list != null) {
            if (list.contains(indexInInventory)) {
                list.rem(indexInInventory);
            } else {
                list.add(indexInInventory);
            }
        } else {
            list = new IntArrayList();
            list.add(indexInInventory);
        }
        SlotLocker.lockedSlots.put(mode, list); // do I need to readd it?
        try {
            SlotLocker.save();
        } catch (IOException e) {
            Chat.send(Chat.addLogo("&4Failed to save SlotLocker settings."));
        }
    }
}
