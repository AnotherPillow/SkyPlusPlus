package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;


import java.util.Objects;

public class CleanGuiBackgrounds {
    public static void generatePredicateProvider(Item item) {
        SkyPlusPlusConfig config = SkyPlusPlusClient.config;
        MinecraftClient client = SkyPlusPlusClient.client;

        ModelPredicateProviderRegistry.register(item, new Identifier("skyplusplus:cleanadgui"), (itemStack, clientWorld, livingEntity, randomSeed) -> {
            HandledScreen<?> screen = ((HandledScreen<?>)client.currentScreen);
            String title = screen == null ? "" : screen.getTitle().getString().trim();
            if (config.cleanAdGuiEnabled) {
                if (Objects.equals(title, "Top Skyblock Islands | Ads") ||
                    Objects.equals(title, "Profile | Ads")
                ) return 0.6f;
            }
            if (config.cleanQuestGuiEnabled) {
                if (Objects.equals(title, "Daily Quests")) return 0.6f;
            }
            return 0.0f;
//            Chat.send("running predicate for clean ai gui, title: " + title);

        });
    }
    public static void register() {
        generatePredicateProvider(Items.WHITE_STAINED_GLASS_PANE);
    }
}
