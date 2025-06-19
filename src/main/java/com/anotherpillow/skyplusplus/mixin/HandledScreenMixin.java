package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.anotherpillow.skyplusplus.features.ShareButton;
import com.anotherpillow.skyplusplus.keybinds.HoverNBTCopy;
import com.anotherpillow.skyplusplus.util.Chat;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin<T extends ScreenHandler> extends Screen implements ScreenHandlerProvider<T> {
    @Shadow
    protected T handler;

    @Shadow
    public abstract T getScreenHandler();
    // WILL BE IGNORED. DO NOT USE
    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @Invoker("isPointOverSlot")
    protected abstract boolean invokeIsPointOverSlot(Slot slot, double pointX, double pointY);

    @Inject(method = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;drawSlot(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/screen/slot/Slot;)V", at = @At("TAIL"))
    public void drawSlot(MatrixStack matrices, Slot slot, CallbackInfo ci) {
//        HandledScreen.drawSprite(matrices, slot.x, slot.y, 20, 16, 16, );
        Pair<Identifier, Identifier> bg = slot.getBackgroundSprite();
        if (bg == null) return;
        Chat.send("x: " + slot.x + "y: " + slot.y);
        Chat.send("\\-> " + bg.getFirst().toString() + ", " + bg.getSecond().toString());


    }

    @Inject(method = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V", at = @At("HEAD"))
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        // https://www.reddit.com/r/fabricmc/comments/15drn3l/comment/ju95aqn
        for(int k = 0; k < this.handler.slots.size(); k++) {
            var slot = this.handler.slots.get(k);
            if (this.invokeIsPointOverSlot(slot, (double) mouseX, (double) mouseY)) {
                HoverNBTCopy.hoveredItem = slot.getStack();

                /* for future reference: get the title.
                if (this.title != null) {

                    Chat.send(this.title);
                }*/
            }
        }
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo info) {
        ScreenHandler handler = getScreenHandler();
        MinecraftClient client = MinecraftClient.getInstance();
        Window window = client.getWindow();
        SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();

        int sw = window.getScaledWidth();
        int sh = window.getScaledHeight();
        int midX = sw / 2;
        int midY = sh / 2;

        /* draw share button */
        if (ShareButton.isApplicable(handler) && config.enableShareButton) {
            addDrawableChild(
                    // public ButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
                    new ButtonWidget(midX + 90, midY - 60, 60, 20, Text.of("Share"), (ButtonWidget widget) -> {
                        CompletableFuture.runAsync(() -> {
//                        handler.slots.forEach(slot -> {
                            int slotCount = ShareButton.getInnerSlots(handler);
                            if (slotCount <= 0) {
                                Chat.send(Chat.addLogo("Cannot share, inventory is not a valid 54/27 stack one."));
                                return;
                            }

                            Gson gson = new Gson();
                            JsonObject json = new JsonObject();
                            JsonArray jsonSlots = new JsonArray();

                            json.addProperty("authorUUID", client.player.getUuidAsString());
                            json.addProperty("authorName", client.player.getName().getString());
                            json.addProperty("server", client.getCurrentServerEntry() == null ? "singleplayer" : client.getCurrentServerEntry().address);
                            json.addProperty("source", "Sky++ Share Button");
                            json.addProperty("containerName", this.title.getString());
                            json.addProperty("containerType", ShareButton.stringifyContainerType(ShareButton.getContainerType(handler)));

                            for (int i = 0; i < slotCount; i ++) {
                                JsonObject slotJson = new JsonObject();
                                Slot slot = handler.getSlot(i);
                                ItemStack stack = slot.getStack();

                                String itemId = Registry.ITEM.getKey(stack.getItem()).get().getValue().toString();

                                // id: MinecraftID,
                                //        Slot: number,
                                //        Count: number,
                                //        tag: GenericNBT,
                                slotJson.addProperty("id", itemId);
                                slotJson.addProperty("Slot", i);
                                slotJson.addProperty("Count", stack.getCount());


                                String jsonNBT = "{}";

                                if (stack.hasNbt()) {
                                    NbtCompound nbt = stack.getNbt();
                                    jsonNBT = NbtOps.INSTANCE.convertTo(JsonOps.INSTANCE, nbt).toString();
                                }

                                JsonObject nbtObject = gson.fromJson(jsonNBT, JsonObject.class);
                                slotJson.add("tag", nbtObject);

                                jsonSlots.add(slotJson);
                            }

                            json.add("slots", jsonSlots);

                            String jsonString = gson.toJson(json);

                            HttpClient http = HttpClient.newBuilder()
                                    .followRedirects(HttpClient.Redirect.ALWAYS)
                                    .version(HttpClient.Version.HTTP_1_1)
                                    .build();
                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create(config.shareApiBaseUrl + "/api/items/upload/container")) // todo: replace with prod
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
                                                        new ClickEvent(ClickEvent.Action.OPEN_URL, url))
                                                .withUnderline(true));
                                MutableText finalMessage = Text.literal("Shared URL: ").append(urlText);
                                Chat.send(Chat.addLogo(finalMessage));
                            } else {
                                Chat.send(Chat.addLogo("Failed to share (status: " + status + ", body: " + body + ")"));
                            }

                        });
                    })
            );
        }

    }
}