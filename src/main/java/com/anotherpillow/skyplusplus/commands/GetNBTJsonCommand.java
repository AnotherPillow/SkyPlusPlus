package com.anotherpillow.skyplusplus.commands;

import com.anotherpillow.skyplusplus.util.Chat;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

public class GetNBTJsonCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        MinecraftClient client = MinecraftClient.getInstance();

        dispatcher.register(ClientCommandManager.literal("getnbtjson").executes(context -> {
            if (client.player == null) {
                return 0;
            }

            client.send(() -> {
                ItemStack mainHand = client.player.getStackInHand(Hand.MAIN_HAND);

                if (!mainHand.hasNbt()) {
                    Chat.send(Chat.addLogo("No NBT found on held item"));
                    return;
                }

                NbtCompound nbt = mainHand.getNbt();
                String jsonNBT = NbtOps.INSTANCE.convertTo(JsonOps.INSTANCE, nbt).toString();
                MutableText txt = Text.literal(jsonNBT)
                        .setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, jsonNBT)).withUnderline(true));

                Chat.send(Chat.addLogo(txt));
            });
            return 1;
        }));
    }
}