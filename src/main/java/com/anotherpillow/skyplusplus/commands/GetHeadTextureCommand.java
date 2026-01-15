package com.anotherpillow.skyplusplus.commands;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.anotherpillow.skyplusplus.util.Chat;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
//? >=1.21 {
/*import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
//}?
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

public class GetHeadTextureCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        MinecraftClient client = MinecraftClient.getInstance();

        //? <1.21 {
        dispatcher.register(ClientCommandManager.literal("getheadtexture").executes(context -> {
            if (client.player == null) {
                return 0;
            }

            client.send(() -> {
                ItemStack mainHand = client.player.getStackInHand(Hand.MAIN_HAND);
                if (!Objects.equals(mainHand.getItem().getTranslationKey(), "block.minecraft.player_head")) {
                    Chat.send(Chat.addLogo("Held item is not a player head."));
                    return;
                }

                if (!mainHand.hasNbt()) {
                    Chat.send(Chat.addLogo("No NBT found on held item"));
                    return;
                }

                //? if >=1.21 {
                /^ComponentMap components = mainHand.getComponents();

                if (components.isEmpty()) {
                    return;
                }


                NbtCompound itemTag = mainHand.getNbt();

                if (itemTag == null) {
                    return;
                }

                if (!itemTag.contains("SkullOwner", NbtElement.COMPOUND_TYPE)) {
                    Chat.send(Chat.addLogo("No SkullOwner tag found"));
                    return;
                }
                NbtCompound skullOwner = itemTag.getCompound("SkullOwner");





                if (!skullOwner.contains("Properties", NbtElement.COMPOUND_TYPE)) {
                    Chat.send(Chat.addLogo("No Properties tag found in SkullOwner"));
                    return;
                }
                NbtCompound properties = skullOwner.getCompound("Properties");

                if (!properties.contains("textures", NbtElement.LIST_TYPE)) {
                    Chat.send(Chat.addLogo("No textures list found"));
                    return;
                }
                NbtList textures = properties.getList("textures", NbtElement.COMPOUND_TYPE);
                if (textures.isEmpty()) {
                    Chat.send(Chat.addLogo("Texture list is empty"));
                    return;
                }
                NbtCompound textureCompound = textures.getCompound(0);

                if (!textureCompound.contains("Value", NbtElement.STRING_TYPE)) {
                    Chat.send(Chat.addLogo("No Value tag found"));
                    return;
                }
                String textureValueEncoded = textureCompound.getString("Value");

                String decodedJson;
                try {
                    byte[] decodedBytes = Base64.getDecoder().decode(textureValueEncoded);
                    decodedJson = new String(decodedBytes, StandardCharsets.UTF_8);
                } catch (IllegalArgumentException e) {
                    Chat.send(Chat.addLogo("Failed to decode texture value: " + e.getMessage()));
                    return;
                }

                String url;
                try {
                    JsonElement jsonElement = JsonParser.parseString(decodedJson);
                    if (!jsonElement.isJsonObject()) {
                        Chat.send(Chat.addLogo("Decoded JSON is not a valid object"));
                        return;
                    }
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (!jsonObject.has("textures")) {
                        Chat.send(Chat.addLogo("JSON has no textures key"));
                        return;
                    }

                    JsonObject texturesObj = jsonObject.getAsJsonObject("textures");
                    if (!texturesObj.has("SKIN")) {
                        Chat.send(Chat.addLogo("JSON has no SKIN object"));
                        return;
                    }

                    JsonObject skinObj = texturesObj.getAsJsonObject("SKIN");
                    if (!skinObj.has("url")) {
                        Chat.send(Chat.addLogo("JSON has no url key in SKIN object"));
                        return;
                    }
                    url = skinObj.get("url").getAsString();
                } catch (Exception e) {
                    Chat.send(Chat.addLogo("Error parsing JSON: " + e.getMessage()));
                    return;
                }

                MutableText urlText = Text.literal(url)
                        .setStyle(Style.EMPTY.withClickEvent(
                                        new ClickEvent(ClickEvent.Action.OPEN_URL, url))
                                .withUnderline(true)
                                .withColor(Formatting.BLUE).withBold(true));

                MutableText finalMessage = Text.literal("Skin texture URL: ").append(urlText);
                Chat.send(Chat.addLogo(finalMessage));
            });
            return 1;
        }));^///?}
    }


}
*/