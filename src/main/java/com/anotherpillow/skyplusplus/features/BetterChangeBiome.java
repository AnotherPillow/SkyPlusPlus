package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class BetterChangeBiome {
    public static void generatePredicateProvider(Item item) {
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
        ModelPredicateProviderRegistry.register(item, new Identifier("skyplusplus:betterchangebiome"), (itemStack, clientWorld, livingEntity, randomSeed) -> {
            if (!config.betterChangeBiomeEnabled) return 0.0f;
            NbtCompound nbtCompound = itemStack.getNbt();

            MinecraftClient client = MinecraftClient.getInstance();
            //client.inGameHud.getChatHud().addMessage(Text.of(itemStack.getItem().toString()));

            if (nbtCompound == null) return 0.0f;
            // client.inGameHud.getChatHud().addMessage(Text.of(nbtCompound.toString()));
            if (itemStack.isOf(Items.WATER_BUCKET)) {//client.inGameHud.getChatHud().addMessage(Text.of("WATERBUCKET: " + nbtCompound.toString()));
                if (!nbtCompound.contains("CHANGE_BIOME")) return 0.0f;
                if (nbtCompound.getString("CHANGE_BIOME").equals("WARM_OCEAN")) return 0.4f;
                else if (nbtCompound.getString("CHANGE_BIOME").equals("RIVER")) return 0.7f;
            }
            if (!nbtCompound.contains("CHANGE_BIOME")) return 0.0f; //yes, this seems to be required bc null check
            //client.inGameHud.getChatHud().addMessage(Text.of("CHANGEBIOME: " + nbtCompound.getString("CHANGE_BIOME")));

            //System.out.println(nbtCompound);
            return 0.6f;
        });
    }
    public static void register() {
        generatePredicateProvider(Items.ALLIUM);
        generatePredicateProvider(Items.OAK_SAPLING);
        generatePredicateProvider(Items.LILY_PAD);
        generatePredicateProvider(Items.ICE);
        generatePredicateProvider(Items.STONE);
        generatePredicateProvider(Items.BAMBOO);
        generatePredicateProvider(Items.RED_MUSHROOM_BLOCK);
        generatePredicateProvider(Items.SAND);
        generatePredicateProvider(Items.PODZOL);
        generatePredicateProvider(Items.ANDESITE);
        generatePredicateProvider(Items.WATER_BUCKET);
        generatePredicateProvider(Items.BIRCH_LOG);
        generatePredicateProvider(Items.SNOW);
        generatePredicateProvider(Items.RED_SAND);
        generatePredicateProvider(Items.DARK_OAK_LOG);
        generatePredicateProvider(Items.VINE);
        generatePredicateProvider(Items.DEAD_BUSH);
    }
}
