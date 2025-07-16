package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.util.Server;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.screen.slot.Slot;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlotLocker {
    public static Slot hoveredSlot = null;
    public static ConcurrentHashMap<Server.Mode, IntArrayList> lockedSlots = new ConcurrentHashMap<>();

    private static final Path CONFIG_FILE = resolveConfigPath("skyplusplus-slots.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type MAP_TYPE = new TypeToken<ConcurrentHashMap<Server.Mode, int[]>>() {}.getType();

    private static Path resolveConfigPath(String fileName) {
        return net.fabricmc.loader.api.FabricLoader.getInstance()
            .getConfigDir()
            .resolve(fileName);
    }

    public static void save() throws IOException {
        // Convert IntArrayList → int[] for JSON
        ConcurrentHashMap<Server.Mode, int[]> plain = new ConcurrentHashMap<>();
        lockedSlots.forEach((mode, list) -> plain.put(mode, list.toIntArray()));

        Files.createDirectories(CONFIG_FILE.getParent());
        try (Writer w = Files.newBufferedWriter(CONFIG_FILE)) {
            GSON.toJson(plain, MAP_TYPE, w);
        }
    }

    public static void load() throws IOException {
        if (!Files.exists(CONFIG_FILE)) return;

        try (Reader r = Files.newBufferedReader(CONFIG_FILE)) {
            Map<Server.Mode, int[]> plain = GSON.fromJson(r, MAP_TYPE);
            lockedSlots.clear();
            if (plain != null) {
                plain.forEach((mode, arr) -> lockedSlots.put(mode, new IntArrayList(arr)));
            }
        }
    }
}
