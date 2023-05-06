package com.anotherpillow.skyplusplus.api;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;

@Environment(EnvType.CLIENT)
public class ModMenuAPI implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return SkyPlusPlusConfig::getConfigScreen;
    }
}