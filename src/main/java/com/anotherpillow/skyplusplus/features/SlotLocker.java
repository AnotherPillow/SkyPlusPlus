package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.util.Server;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.screen.slot.Slot;

import java.util.concurrent.ConcurrentHashMap;

public class SlotLocker {
    public static Slot hoveredSlot = null;
    public static ConcurrentHashMap<Server.Mode, IntArrayList> lockedSlots = new ConcurrentHashMap<>();
}
