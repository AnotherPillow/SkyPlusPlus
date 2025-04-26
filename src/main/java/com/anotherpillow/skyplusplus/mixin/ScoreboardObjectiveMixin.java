package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.anotherpillow.skyplusplus.util.Server;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ScoreboardObjective.class)
public class ScoreboardObjectiveMixin {
    SkyPlusPlusConfig config = SkyPlusPlusConfig.configInstance.getConfig();
    @Shadow private Text displayName;

    /**
     * @author AnotherPillow
     * @reason Change scoreboard  display name
     */
    @Overwrite
    public Text getDisplayName() {
        if (!config.dynamicScoreboardTitle) return this.displayName;

        return switch (Server.getSkyblockMode()) {
            case MOBARENA ->
                Text.empty()
                        .append(Text.of("Skyblock ").copy().formatted(Formatting.BOLD).formatted(Formatting.GREEN))
                        .append(Text.translatable("skyplusplus.server.mobarena").copy().formatted(Formatting.BOLD).formatted(Formatting.GOLD));
            case ECONOMY -> Text.empty()
                    .append(Text.of("Skyblock ").copy().formatted(Formatting.BOLD).formatted(Formatting.GREEN))
                    .append(Text.translatable("skyplusplus.server.economy").copy().formatted(Formatting.BOLD).formatted(Formatting.DARK_PURPLE));
            case SURVIVAL -> Text.empty()
                    .append(Text.of("Skyblock ").copy().formatted(Formatting.BOLD).formatted(Formatting.GREEN))
                    .append(Text.translatable("skyplusplus.server.skyblock").copy().formatted(Formatting.BOLD).formatted(Formatting.DARK_GREEN));
            case HUB -> Text.empty()
                    .append(Text.of("Skyblock ").copy().formatted(Formatting.BOLD).formatted(Formatting.GREEN))
                    .append(Text.translatable("skyplusplus.server.hub").copy().formatted(Formatting.BOLD).formatted(Formatting.BLUE));
            default -> this.displayName;
        };
    }
}
