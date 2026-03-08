package com.anotherpillow.skyplusplus.features.mobarena;

import net.minecraft.entity.EntityType;
import net.minecraft.text.Text;

/*
    position starts at 1.
 */
public record Wave(int position, WaveType type,
                   EntityType<?> bossEntity,
                   String bossText,
                   String textColour // e.g. §5
) {
    public boolean isBoss() {
        return type == WaveType.BOSS;
    }
}
