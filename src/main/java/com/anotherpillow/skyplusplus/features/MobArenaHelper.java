package com.anotherpillow.skyplusplus.features;

import com.anotherpillow.skyplusplus.client.SkyPlusPlusClient;
import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.anotherpillow.skyplusplus.features.mobarena.Wave;
import com.anotherpillow.skyplusplus.features.mobarena.WaveType;
import com.anotherpillow.skyplusplus.util.Chat;
import com.anotherpillow.skyplusplus.util.Server;
import com.anotherpillow.skyplusplus.util.StringChecker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
//? if >=1.20.1 {
/*import net.minecraft.client.gui.DrawContext;
 *///?} else {
import net.minecraft.client.gui.DrawableHelper;
//?}
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobArenaHelper {
    private static int lastKnownWave = 0;

    public static void onWave(String message) {
        Pattern p = Server.getMobArena() == Server.Arena.SB ? StringChecker.mobArenaWavePattern : StringChecker.catacylsmWavePattern;
        Matcher m = p.matcher(message);
        if (!m.matches()) return;

        int wave = Integer.parseInt(m.group(1));
        WaveType type = WaveType.valueOf(m.group(2) == null ? "NORMAL" : m.group(2));

        MobArenaHelper.lastKnownWave = wave;
    }

    public static void onFinish() {
        lastKnownWave = 0;
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
            Server.Arena.SB, Map.ofEntries(
                    Map.entry(10, new Wave(10, WaveType.BOSS, EntityType.HUSK, "§2Dustbane ★", "§2")),
                    Map.entry(20, new Wave(20, WaveType.BOSS, EntityType.SPIDER, "§4Venomix ★", "§4")),
                    Map.entry(30, new Wave(30, WaveType.BOSS, EntityType.PIGLIN_BRUTE, "§6Firelin ★", "§6")),
                    Map.entry(40, new Wave(40, WaveType.BOSS, EntityType.WITCH, "§5Grimwort ★", "§5")),
                    Map.entry(50, new Wave(50, WaveType.BOSS, EntityType.WARDEN, "§3Titan ★", "§3")),

                    // sourced from wiki
                    Map.entry(1, new Wave(1, WaveType.NORMAL, null, "§8Normal", "§8")),
                    Map.entry(5, new Wave(5, WaveType.NORMAL, null, "§8Normal", "§8")),
                    Map.entry(15, new Wave(15, WaveType.NORMAL, null, "§8Normal", "§8")),
                    Map.entry(25, new Wave(25, WaveType.NORMAL, null, "§8Normal", "§8")),
                    Map.entry(35, new Wave(35, WaveType.NORMAL, null, "§8Normal", "§8")),
                    Map.entry(45, new Wave(45, WaveType.NORMAL, null, "§8Normal", "§8")),

                    Map.entry(2, new Wave(2, WaveType.NETHER, null, "§4Nether", "§4")),
                    Map.entry(7, new Wave(7, WaveType.NETHER, null, "§4Nether", "§4")),
                    Map.entry(12, new Wave(12, WaveType.NETHER, null, "§4Nether", "§4")),
                    Map.entry(17, new Wave(17, WaveType.NETHER, null, "§4Nether", "§4")),
                    Map.entry(22, new Wave(22, WaveType.NETHER, null, "§4Nether", "§4")),
                    Map.entry(27, new Wave(27, WaveType.NETHER, null, "§4Nether", "§4")),
                    Map.entry(32, new Wave(32, WaveType.NETHER, null, "§4Nether", "§4")),
                    Map.entry(37, new Wave(37, WaveType.NETHER, null, "§4Nether", "§4")),
                    Map.entry(42, new Wave(42, WaveType.NETHER, null, "§4Nether", "§4")),
                    Map.entry(47, new Wave(47, WaveType.NETHER, null, "§4Nether", "§4")),

                    Map.entry(3, new Wave(3, WaveType.END, null, "§eEnd", "§e")),
                    Map.entry(13, new Wave(13, WaveType.END, null, "§eEnd", "§e")),
                    Map.entry(23, new Wave(23, WaveType.END, null, "§eEnd", "§e")),
                    Map.entry(33, new Wave(33, WaveType.END, null, "§eEnd", "§e")),
                    Map.entry(43, new Wave(43, WaveType.END, null, "§eEnd", "§e")),

                    Map.entry(6, new Wave(6, WaveType.PILLAGER, null, "§3Pillager", "§3")),
                    Map.entry(16, new Wave(16, WaveType.PILLAGER, null, "§3Pillager", "§3")),
                    Map.entry(26, new Wave(26, WaveType.PILLAGER, null, "§3Pillager", "§3")),
                    Map.entry(36, new Wave(36, WaveType.PILLAGER, null, "§3Pillager", "§3")),
                    Map.entry(46, new Wave(46, WaveType.PILLAGER, null, "§3Pillager", "§3")),

                    Map.entry(4, new Wave(4, WaveType.SPECIAL, null, "§bSpecial", "§b")),
                    Map.entry(8, new Wave(8, WaveType.SPECIAL, null, "§bSpecial", "§b")),
                    Map.entry(14, new Wave(14, WaveType.SPECIAL, null, "§bSpecial", "§b")),
                    Map.entry(18, new Wave(18, WaveType.SPECIAL, null, "§bSpecial", "§b")),
                    Map.entry(24, new Wave(24, WaveType.SPECIAL, null, "§bSpecial", "§b")),
                    Map.entry(28, new Wave(28, WaveType.SPECIAL, null, "§bSpecial", "§b")),
                    Map.entry(34, new Wave(34, WaveType.SPECIAL, null, "§bSpecial", "§b")),
                    Map.entry(38, new Wave(38, WaveType.SPECIAL, null, "§bSpecial", "§b")),
                    Map.entry(44, new Wave(44, WaveType.SPECIAL, null, "§bSpecial", "§b")),
                    Map.entry(48, new Wave(48, WaveType.SPECIAL, null, "§bSpecial", "§b")),

                    Map.entry(11, new Wave(11, WaveType.SWARM, null, "§9Swarm", "§9")),
                    Map.entry(21, new Wave(21, WaveType.SWARM, null, "§9Swarm", "§9")),
                    Map.entry(31, new Wave(31, WaveType.SWARM, null, "§9Swarm", "§9")),
                    Map.entry(41, new Wave(41, WaveType.SWARM, null, "§9Swarm", "§9")),

                    Map.entry(9, new Wave(9, WaveType.UPGRADE, null, "§6Upgrade", "§6")),
                    Map.entry(19, new Wave(19, WaveType.UPGRADE, null, "§6Upgrade", "§6")),
                    Map.entry(29, new Wave(29, WaveType.UPGRADE, null, "§6Upgrade", "§6")),
                    Map.entry(39, new Wave(39, WaveType.UPGRADE, null, "§6Upgrade", "§6")),
                    Map.entry(49, new Wave(49, WaveType.UPGRADE, null, "§6Upgrade", "§6"))
            ),
            Server.Arena.CATACLYSM, Map.of(
                    10, new Wave(10, WaveType.BOSS, EntityType.CREEPER, "§bThermo ★", "§b"), // charged creeper technically
                    // todo: colours
                    25, new Wave(25, WaveType.BOSS, EntityType.MAGMA_CUBE, "§fOb'Lazrak ★", "§f"),
                    40, new Wave(40, WaveType.BOSS, EntityType.SKELETON, "§fNecromancer ★", "§f"),
                    50, new Wave(50, WaveType.BOSS, EntityType.MAGMA_CUBE, "§fDoom of the Nullvoid ★", "§f"),
                    60, new Wave(60, WaveType.BOSS, EntityType.WITHER, "§fArbiter ★", "§f")
            ) // TODO
    );
}
