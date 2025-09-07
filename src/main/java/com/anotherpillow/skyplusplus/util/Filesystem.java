package com.anotherpillow.skyplusplus.util;

import java.nio.file.Path;

public class Filesystem {
    public static Path resolveConfigPath(String fileName) {
        return net.fabricmc.loader.api.FabricLoader.getInstance()
                .getConfigDir()
                .resolve(fileName);
    }
}
