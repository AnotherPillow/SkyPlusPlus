package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import net.minecraft.client.MinecraftClient;
//? if <1.21 {
/*import net.minecraft.client.item.ModelPredicateProviderRegistry;
 *///?}
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

public class ShowEmptyShops {
    public static void generatePredicateProvider(Item item) {
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
        MinecraftClient client = MinecraftClient.getInstance();
        //? if <1.21 {
        /*ModelPredicateProviderRegistry.register(item, new Identifier("skyplusplus:empty_shop"), (itemStack, clientWorld, livingEntity, randomSeed) -> {
            if (!config.showEmptyShopsEnabled) return 0.0f;
            NbtCompound nbtCompound = itemStack.getNbt();

            if (nbtCompound == null) return 0.0f;
            if (nbtCompound.toString().contains("\"text\":\"0\"}],\"text\":\"Remaining trades: \"}")) return 0.6f;


            return 0.0f;
        });
        *///?}
    }
    public static void register() {
        generatePredicateProvider(Items.CHEST);
    }
}
