package com.anotherpillow.skyplusplus.mixin;

import com.anotherpillow.skyplusplus.util.Server;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ScoreboardObjective.class)
public class ScoreboardObjectiveMixin {

    @Shadow private Text displayName;

    /**
     * @author
     * @reason
     */
    @Overwrite
    public Text getDisplayName() {
        return switch (Server.getSkyblockMode()) {
            case ECONOMY -> Text.empty()
                    .append(Text.of("Skyblock ").copy().formatted(Formatting.BOLD).formatted(Formatting.GREEN))
                    .append(Text.of("Economy").copy().formatted(Formatting.BOLD).formatted(Formatting.DARK_PURPLE));
            case SURVIVAL -> Text.empty()
                    .append(Text.of("Skyblock ").copy().formatted(Formatting.BOLD).formatted(Formatting.GREEN))
                    .append(Text.of("Survival").copy().formatted(Formatting.BOLD).formatted(Formatting.DARK_GREEN));
            case HUB -> Text.empty()
                    .append(Text.of("Skyblock ").copy().formatted(Formatting.BOLD).formatted(Formatting.GREEN))
                    .append(Text.of("Hub").copy().formatted(Formatting.BOLD).formatted(Formatting.BLUE));
            default -> this.displayName;
        };
    }
}
