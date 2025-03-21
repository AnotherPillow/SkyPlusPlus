package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.util.Chat;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

public class ShareButton {

    private static final List validScreens  = List.of(ScreenHandlerType.GENERIC_9X3, ScreenHandlerType.GENERIC_9X6, ScreenHandlerType.SHULKER_BOX);
    public static enum ContainerType {
        SHULKER_BOX,
        DOUBLE_CHEST,
        CHEST,
        UNKNOWN,
    }

    public static boolean isApplicable(ScreenHandler handler) {
        try {
            return validScreens.contains(handler.getType());
        } catch (UnsupportedOperationException e) {
//            Chat.send(Chat.addLogo("Failed to get screen handler type for inventory"));
            return false; // inventory does this
        }
    }

    public static int getInnerSlots(ScreenHandler handler) {
        ScreenHandlerType<?> type = handler.getType();
        if (type.equals(ScreenHandlerType.GENERIC_9X3)) {
            return 27;
        } else if (type.equals(ScreenHandlerType.GENERIC_9X6)) {
            return 54;
        } else if (type.equals(ScreenHandlerType.SHULKER_BOX)) {
            return 27;
        }
        return -1;
    }

    public static ContainerType getContainerType(ScreenHandler handler) {
        ScreenHandlerType<?> type = handler.getType();
        if (type.equals(ScreenHandlerType.GENERIC_9X3)) {
            return ContainerType.CHEST;
        } else if (type.equals(ScreenHandlerType.GENERIC_9X6)) {
            return ContainerType.DOUBLE_CHEST;
        } else if (type.equals(ScreenHandlerType.SHULKER_BOX)) {
            return ContainerType.SHULKER_BOX;
        }
        return ContainerType.UNKNOWN;
    }

    public static String stringifyContainerType(ContainerType type) {
        if (type == ContainerType.DOUBLE_CHEST) return "double_chest";
        if (type == ContainerType.SHULKER_BOX) return "shulker_box";
        return "chest";
    }
}
