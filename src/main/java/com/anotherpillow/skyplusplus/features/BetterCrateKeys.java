package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Formatting;

import java.util.Objects;

public class BetterCrateKeys {
    public static void generatePredicateProvider(Item item) {
        SkyPlusPlusConfig config = SkyPlusPlusClient.config;
        ModelPredicateProviderRegistry.register(item, new Identifier("skyplusplus:bettercratekeys"), (itemStack, clientWorld, livingEntity, randomSeed) -> {
            if (!config.betterCrateKeysEnabled) return 0.0f;
            NbtCompound nbtCompound = itemStack.getNbt();

            MinecraftClient client = MinecraftClient.getInstance();
            //client.inGameHud.getChatHud().addMessage(Text.of(itemStack.getItem().toString()));

            if (nbtCompound == null) return 0.0f;
            // client.inGameHud.getChatHud().addMessage(Text.of(nbtCompound.toString()));
            NbtList loreList = nbtCompound .getCompound("display").getList("Lore", NbtElement.STRING_TYPE);

            if (loreList.isEmpty()) return 0.0f;

            String loreText = loreList.getString(0);
            if (loreText == null) return 0.0f;

            //? if >=1.20.4 {
            String lore = TextCodecs.CODEC.parse(JsonOps.INSTANCE, JsonParser.parseString(loreText)).get().orThrow().getString();
             //?} else {
            /*String lore = Text.Serializer.fromJson(loreText).getString();
            *///?}

            // client.inGameHud.getChatHud().addMessage(Text.of(lore));


            return switch (lore) {
                case "Click the Voting Crate to use this key", "Click the Voter Crate to use this key" -> 0.1f;
                case "Click the Common Crate to use this key" -> 0.3f;
                case "Click the Rare Crate to use this key" -> 0.5f;
                case "Click the Epic Crate to use this key" -> 0.7f;
                case "Click the Legendary Crate to use this key" -> 0.9f;
                default -> 0.0f;
            };
        });
    }
    public static void register() {
        generatePredicateProvider(Items.TRIPWIRE_HOOK);
    }
}
