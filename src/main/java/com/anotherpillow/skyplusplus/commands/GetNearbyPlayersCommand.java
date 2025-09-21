package com.anotherpillow.skyplusplus.commands;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.util.Chat;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GetNearbyPlayersCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        MinecraftClient client = MinecraftClient.getInstance();

        dispatcher.register(ClientCommandManager.literal("nearbyplayerinfo").executes(context -> {

            getPlayers();
            for (AbstractClientPlayerEntity player : players) {
                Chat.send(Text.empty()
                        .append("name: ")
                        .append(player.getName())
                        .append(", entity name: ")
                        .append(Text.of(player.getEntityName() == null ? "<null>" : player.getEntityName()))
                        .append(", custom name: ")
                        .append(player.getCustomName() == null ? Text.of("<null>") : player.getCustomName())
                        .append(", display name: ")
                        .append(player.getDisplayName() == null ? Text.of("<null>") : player.getDisplayName())
                        .append(Text.of(" ("))
                        .append(Text.of(player.getUuidAsString()))
                        .append(Text.of("), Cape: "))
                        .append(Text.of(player.getCapeTexture() == null ? "N/A" : player.getCapeTexture().toString()))
                        .append(Text.of(", Skin: "))
                        .append(Text.of(player.getSkinTexture() == null ? "N/A" : player.getSkinTexture().toString()))
                );
            }

            return 1;
        }));
    }


    //  https://github.com/MeteorDevelopment/meteor-client/blob/a2fa3cbfaaeb5bfe3c01a293d01eea92a210a5ba/src/main/java/meteordevelopment/meteorclient/systems/hud/elements/PlayerRadarHud.java#L203C5-L210C6
    private static final List<AbstractClientPlayerEntity> players = new ArrayList<>();
    private static List<AbstractClientPlayerEntity> getPlayers() {
        assert SkyPlusPlusClient.client.world != null;

        players.clear();
        players.addAll(SkyPlusPlusClient.client.world.getPlayers());

        players.sort(Comparator.comparingDouble(e -> e.squaredDistanceTo(SkyPlusPlusClient.client.getCameraEntity())));

        return players;
    }
}
