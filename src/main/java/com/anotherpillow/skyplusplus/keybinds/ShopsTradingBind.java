package com.anotherpillow.skyplusplus.keybinds;

import com.anotherpillow.skyplusplus.features.SlotLocker;
import com.anotherpillow.skyplusplus.util.Chat;
import com.anotherpillow.skyplusplus.util.Server;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ShopsTradingBind {
    public static KeyBinding buyingBinding;
    public static KeyBinding sellingBinding;
    public static Slot hoveredSlot = null;
    private static Timer timer = new Timer();

    public static void register() {
        buyingBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "skyplusplus.keybind.shopsbuyingitem",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT,
                "Sky++"
        ));
        sellingBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "skyplusplus.keybind.shopssellingitem",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT,
                "Sky++"
        ));
    }

    public static void logic(MinecraftClient client, boolean isBuying) {

        HandledScreen<?> currentScreen = (HandledScreen<?>) client.currentScreen;

        ScreenHandler handler = currentScreen.getScreenHandler();

        client.interactionManager.clickSlot(
                handler.syncId, hoveredSlot.id,
                //? if >=1.21 {
                /*client.player.getInventory().getSelectedSlot(),
                *///?} else {
                client.player.getInventory().selectedSlot,
                //?}
                SlotActionType.SWAP, client.player
        );

        Chat.sendCommandToServer("shops find " + (isBuying ? "wanted" : "owned"));

        client.interactionManager.clickSlot(
                handler.syncId, hoveredSlot.id,
                //? if >=1.21 {
                /*client.player.getInventory().getSelectedSlot(),
                *///?} else {
                client.player.getInventory().selectedSlot,
                 //?}
                SlotActionType.SWAP, client.player
        );
    }
}
