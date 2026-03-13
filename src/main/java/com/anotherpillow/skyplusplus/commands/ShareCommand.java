package com.anotherpillow.skyplusplus.commands;

import com.anotherpillow.skyplusplus.SkyPlusPlus;
import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.anotherpillow.skyplusplus.util.Chat;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
//? if >1.19.2 {
/*import net.minecraft.registry.Registries;
*///?} else {
import net.minecraft.util.registry.Registry;
//?}

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

public class ShareCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        MinecraftClient client = MinecraftClient.getInstance();

        dispatcher.register(ClientCommandManager.literal("share").executes(context -> {
            SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
            if (client.player == null) {
                return 0;
            }

            //? if >=1.21 {
            /*Runnable placeholder = () -> {
            *///?} else {
            CompletableFuture.runAsync(() -> {
             //?}

                ItemStack mainHand = client.player.getStackInHand(Hand.MAIN_HAND);

                String jsonNBT = "{}";
                //? if >=1.21 {
                //?} else {
                if (mainHand.hasNbt()) {
                    NbtCompound nbt = mainHand.getNbt();
                    jsonNBT = NbtOps.INSTANCE.convertTo(JsonOps.INSTANCE, nbt).toString();
                }
                 //?}


                //? if >1.19.2 {
                /*String itemId = Registries.ITEM.getKey(mainHand.getItem()).get().getValue().toString();
                *///?} else {
                String itemId = Registry.ITEM.getKey(mainHand.getItem()).get().getValue().toString();
                //?}

                if (jsonNBT.equals("{}")) return;

                Gson gson = new Gson();
                JsonObject json = new JsonObject();

                JsonObject nbtObject = gson.fromJson(jsonNBT, JsonObject.class);
                json.add("nbt", nbtObject);

                json.addProperty("id", itemId);
                json.addProperty("authorUUID", client.player.getUuidAsString());
                json.addProperty("authorName", client.player.getName().getString());
                json.addProperty("server", client.getCurrentServerEntry() == null ? "singleplayer" : client.getCurrentServerEntry().address);
                json.addProperty("source", "Sky++ /share");

                String jsonString = gson.toJson(json);

                HttpClient http = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS)
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(config.shareApiBaseUrl + "/api/items/upload/single")) // todo: replace with prod
                        .header("Content-Type", "application/json")
                        .header("Content-Type", "application/json; charset=UTF-8")
                        .header("User-Agent", "skyplusplus / " + client.getName())
                        .POST(HttpRequest.BodyPublishers.ofString(jsonString, StandardCharsets.UTF_8))
                        .build();
                HttpResponse<String> response = null;
                try {
                    response = http.send(request, HttpResponse.BodyHandlers.ofString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int status = response.statusCode();
                String body = response.body();

                JsonObject bodyJson = gson.fromJson(body, JsonObject.class);
                if (bodyJson.has("error")) {
                    Chat.send(Chat.addLogo("Failed to share: " + bodyJson.get("error").getAsString() + " (" + status + ")"));
                } else if (bodyJson.has("message")){
                    String url = bodyJson.get("message").getAsString();
                    MutableText urlText = Text.literal(url)
                        .setStyle(Style.EMPTY.withClickEvent(
                                //? if >=1.21 {
                                /*new ClickEvent.OpenUrl(URI.create(url)))
                                *///?} else {
                                new ClickEvent(ClickEvent.Action.OPEN_URL, url))
                                 //?}

                            .withUnderline(true));
                    MutableText finalMessage = Text.literal("Shared URL: ").append(urlText);
                    Chat.send(Chat.addLogo(finalMessage));
                } else {
                    Chat.send(Chat.addLogo("Failed to share (status: " + status + ", body: " + body + ")"));
                }



            //? if >=1.21 {
            /*};
            *///?} else {
            });
             //?}
            return 1;
        }));
    }
}
