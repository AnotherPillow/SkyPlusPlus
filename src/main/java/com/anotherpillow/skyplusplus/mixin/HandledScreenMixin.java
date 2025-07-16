package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.anotherpillow.skyplusplus.features.ShareButton;
import com.anotherpillow.skyplusplus.features.SlotLocker;
import com.anotherpillow.skyplusplus.keybinds.HoverNBTCopy;
import com.anotherpillow.skyplusplus.keybinds.LockSlotBind;
import com.anotherpillow.skyplusplus.util.Chat;
import com.anotherpillow.skyplusplus.util.Server;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
//? if >=1.20.1 {
/*import net.minecraft.client.gui.DrawContext;
 *///?} else {
//?}
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.InputUtil;
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
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
//? if >1.19.2 {
/*import net.minecraft.registry.Registries;
*///?} else {
import net.minecraft.util.registry.Registry;
 //?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(HandledScreen.class)
public abstract class HandledScreenMixin<T extends ScreenHandler> extends Screen implements ScreenHandlerProvider<T> {
    @Shadow
    protected T handler;

    @Shadow
    protected int x;

    @Shadow
    protected int y;

    @Shadow
    public abstract T getScreenHandler();

    @Shadow
    public static void drawSlotHighlight(MatrixStack matrices, int x, int y, int z) {}

    // WILL BE IGNORED. DO NOT USE
    protected HandledScreenMixin(Text title) {
        super(title);
    }

    @Invoker("isPointOverSlot")
    protected abstract boolean invokeIsPointOverSlot(Slot slot, double pointX, double pointY);

    @Inject(method = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;drawSlot(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/screen/slot/Slot;)V", at = @At("HEAD"))
    public void drawSlot(MatrixStack matrices, Slot slot, CallbackInfo ci) {
//        HandledScreen.drawSprite(matrices, slot.x, slot.y, 20, 16, 16, );
//        Pair<Identifier, Identifier> bg = slot.getBackgroundSprite();
//        slot.bac
//        if (bg == null || this.client == null) return;
        HandledScreen<?> screen = (HandledScreen<?>) (Object) this;
        ScreenHandler handler = screen.getScreenHandler();   // 1.19+ : screen.getScreenHandler()
//        int totalSlots = handler.slots.size();               // every slot shown on this screen
//        int thisSlotIndex = slot.id;
//        int x = slot.x;                                  // left coordinate on the texture
//        int y = slot.y;                                  // top  coordinate on the texture
//        int w = 16;                                      // width  (vanilla slots are 16×16)
//        int h = 16;                                      // height

        if (this.client == null) return;

        // why? idk
        int playerInvStart = handler.slots.size() - (this.client.currentScreen instanceof InventoryScreen ? 37 : 36);
        int indexInInventory = slot.id - playerInvStart;

        // Example: print the size of the currently open container
//        Chat.send(String.format("Container has %d slots (drawing slot %d at %d,%d)%n",
//                totalSlots, thisSlotIndex, x, y));

//        if (this.client == null || !(this.client.currentScreen instanceof InventoryScreen)) return;
        IntArrayList lockedSlots = SlotLocker.lockedSlots.get(Server.getSkyblockMode());
        if (lockedSlots != null && lockedSlots.contains(indexInInventory)) {
            RenderSystem.setShaderTexture(0, SkyPlusPlusClient.lockId);
            DrawableHelper.drawTexture(
                    matrices,
                    slot.x,
                    slot.y,
                    0f,
                    0f,
                    16,
                    16,
                    16,
                    16
            );
        }


//        Chat.send("x: " + slot.x + "y: " + slot.y);
//        Chat.send("\\-> " + bg.getFirst().toString() + ", " + bg.getSecond().toString());
    }

    //? if >=1.20.1 {
    /*@Inject(method = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;render(Lnet/minecraft/client/gui/DrawContext;IIF)V", at = @At("HEAD"))
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
    *///?} else {
    @Inject(method = "Lnet/minecraft/client/gui/screen/ingame/HandledScreen;render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V", at = @At("HEAD"))
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
     //?}
        // https://www.reddit.com/r/fabricmc/comments/15drn3l/comment/ju95aqn
        for(int k = 0; k < this.handler.slots.size(); k++) {
            var slot = this.handler.slots.get(k);
            if (this.invokeIsPointOverSlot(slot, (double) mouseX, (double) mouseY)) {
                HoverNBTCopy.hoveredItem = slot.getStack();
                SlotLocker.hoveredSlot = slot;

                /* for future reference: get the title.
                if (this.title != null) {

                    Chat.send(this.title);
                }*/
            }
        }
    }

    @Inject(method = "keyPressed(III)Z", at = @At("HEAD"), cancellable = true)
    public void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        InputUtil.Key lockslotKey = KeyBindingHelper.getBoundKeyOf(LockSlotBind.binding);

        if (keyCode == lockslotKey.getCode()) {
            LockSlotBind.logic(MinecraftClient.getInstance());
            cir.cancel();
        }
    }

    // quick moving with keys still just runs onMouseClick
    @Inject(method = "onMouseClick(Lnet/minecraft/screen/slot/Slot;IILnet/minecraft/screen/slot/SlotActionType;)V", at = @At("HEAD"), cancellable = true)
    public void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType, CallbackInfo ci) {
        IntArrayList list = SlotLocker.lockedSlots.get(Server.getSkyblockMode());
        if (this.client == null || slot == null || list == null) return;

        HandledScreen<?> screen = (HandledScreen<?>) (Object) this;
        ScreenHandler handler = screen.getScreenHandler();
        int playerInvStart = handler.slots.size() - (this.client.currentScreen instanceof InventoryScreen ? 37 : 36);
        int indexInInventory = slot.id - playerInvStart;

        if (list.contains(indexInInventory)) ci.cancel();
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
                    //? if >1.19.2 {
                    /*new ButtonWidget.Builder(Text.of("Share"), (ButtonWidget widget) -> {
                        *///?} else {
                        new ButtonWidget(midX + 90, midY - 60, 60, 20, Text.of("Share"), (ButtonWidget widget) -> {
                         //?}
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

                            for (int i = 0; i < slotCount; i++) {
                                JsonObject slotJson = new JsonObject();
                                Slot slot = handler.getSlot(i);
                                ItemStack stack = slot.getStack();

                                //? if >1.19.2 {
                                /*String itemId = Registries.ITEM.getKey(stack.getItem()).get().getValue().toString();
                                *///?} else {
                                String itemId = Registry.ITEM.getKey(stack.getItem()).get().getValue().toString();
                                 //?}

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
                            } else if (bodyJson.has("message")) {
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
                    //? if >1.19.2 {
                            /*.position(midX + 90, midY - 60).size(60, 20).build()
                    *///?} else {
                    
                     //?}
            );
        }

    }
}