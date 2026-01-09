package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
//? if <1.21 {
/*import net.minecraft.client.item.ModelPredicateProviderRegistry;
 *///?}
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;


import java.util.Objects;

public class CleanGuiBackgrounds {
    public static void generatePredicateProvider(Item item) {
        SkyPlusPlusConfig config = SkyPlusPlusClient.config;
        MinecraftClient client = SkyPlusPlusClient.client;

        //? if <1.21 {
        /*ModelPredicateProviderRegistry.register(item, new Identifier("skyplusplus:cleanadgui"), (itemStack, clientWorld, livingEntity, randomSeed) -> {
            if (client.currentScreen instanceof ChatScreen) return 0.0f;
            try {
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
            } catch (Exception e) {
                return 0.0f;
            }


//            Chat.send("running predicate for clean ai gui, title: " + title);

        });
        *///?}
    }
    public static void register() {
        generatePredicateProvider(Items.WHITE_STAINED_GLASS_PANE);
    }
}
