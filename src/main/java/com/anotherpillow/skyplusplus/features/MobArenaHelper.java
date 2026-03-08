package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.anotherpillow.skyplusplus.features.mobarena.Wave;
import com.anotherpillow.skyplusplus.features.mobarena.WaveType;
import com.anotherpillow.skyplusplus.util.Server;
import com.anotherpillow.skyplusplus.util.StringChecker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class MobArenaHelper {
    private static int lastKnownWave = 0;

    public static void onWave(String message) {
        Matcher m = StringChecker.mobArenaWavePattern.matcher(message);
        if (!m.matches()) return;

        int wave = Integer.parseInt(m.group(1));
        WaveType type = WaveType.valueOf(m.group(2) == null ? "NORMAL" : m.group(2));

        MobArenaHelper.lastKnownWave = wave;
    }

    public static class WaveDisplay {

        public static void render(/*? >=1.20.1 {*/ /*DrawContext drawMatrix *//*?} else {*/ MatrixStack drawMatrix /*?}*/) {
            SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

            // not draw in f1
            if (SkyPlusPlusClient.client.options.hudHidden) return;
            if (Server.getMobArena() == Server.Arena.NONE) return;
            if (!config.waveDisplayEnabled) return;

//            int textWidth = textRenderer.getWidth(txt);
//            int textOffset = textWidth / 2;

            // show [first, prev, current, next, last]
            int relativeWaveOrigin = Math.max(2, MobArenaHelper.lastKnownWave); // so will never have <1 listed
            List<Integer> waveNumbersToDisplay = List.of(1, relativeWaveOrigin - 1,
                    relativeWaveOrigin, relativeWaveOrigin + 1,
                    MobArenaHelper.ARENA_ROUND_COUNTS.getOrDefault(Server.getMobArena(), 50)
            );

            List<Wave> wavesToDisplay = waveNumbersToDisplay.stream().map((n) ->
                MobArenaHelper.ARENA_WAVES.get(Server.getMobArena()).getOrDefault(
                        n, new Wave(n, WaveType.NORMAL, null, "", "§7"))
            ).toList();

            WaveDisplay.drawText(drawMatrix, textRenderer, Text.of("§6§lWaves"),
                    config.waveDisplayX + 32, config.waveDisplayY + 16 + (textRenderer.fontHeight*-1));

            for (int i = 0; i < wavesToDisplay.size(); i++) {
                Wave wave = wavesToDisplay.get(i);
                Wave fancyWave = MobArenaHelper.ARENA_WAVES.get(Server.getMobArena()).get(wave.position());

                Text txt = Text.of(String.format("%s %s§l%s %s",
                        wave.position() == lastKnownWave ? "§a>" : " ",
                        wave.textColour(), wave.position(), fancyWave != null ? "§7- §r" + fancyWave.bossText() : ""));

                WaveDisplay.drawText(drawMatrix, textRenderer, txt,
                        config.waveDisplayX + 32, config.waveDisplayY + 16 + (textRenderer.fontHeight*i));
            }


        }

        private static void drawText(/*? >=1.20.1 {*/ /*DrawContext drawMatrix *//*?} else {*/ MatrixStack drawMatrix /*?}*/, TextRenderer textRenderer, Text txt, int x, int y) {
            //? if >=1.20.1 {
            /*drawMatrix.drawTextWithShadow(textRenderer, txt, x, y, 0xFFFFFF);
             *///?} else if >1.19.2 {
            /*DrawableHelper.drawTextWithShadow(drawMatrix, textRenderer, txt, x, y, 0xFFFFFF);
             *///?} else {
            DrawableHelper.drawTextWithShadow(drawMatrix, textRenderer, txt, x, y, 0xFFFFFF);
            //?}
        }
    }

    private static final Map<Server.Arena, Integer> ARENA_ROUND_COUNTS = Map.of(
            Server.Arena.CATACLYSM, 60,
            Server.Arena.SB, 50
    );

    private static final Map<Server.Arena, Map<Integer, Wave>> ARENA_WAVES = Map.of(
            Server.Arena.SB, Map.of(
                    10, new Wave(10, WaveType.BOSS, EntityType.HUSK, "§2Dustbane", "§2"),
                    20, new Wave(20, WaveType.BOSS, EntityType.SPIDER, "§4Venomix", "§4"),
                    30, new Wave(30, WaveType.BOSS, EntityType.PIGLIN_BRUTE, "§6Firelin", "§6"),
                    40, new Wave(40, WaveType.BOSS, EntityType.WITCH, "§5Grimwort", "§5"),
                    50, new Wave(50, WaveType.BOSS, EntityType.WARDEN, "§3Titan", "§3")
            ),
            Server.Arena.CATACLYSM, Map.of() // TODO
    );
}
