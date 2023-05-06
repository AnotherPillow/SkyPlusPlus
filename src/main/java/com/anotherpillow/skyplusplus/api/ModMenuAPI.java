package com.anotherpillow.skyplusplus.api;

import com.anotherpillow.skyplusplus.config.SkyPlusPlusConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuAPI implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return SkyPlusPlusConfig::getConfigScreen;
    }
}
