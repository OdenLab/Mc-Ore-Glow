package com.github.oreglow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public final class OreGlowMod implements ModInitializer {
    public static final String MOD_ID = "oreglow";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve(MOD_ID + ".json");
    private static volatile OreGlowConfig config = new OreGlowConfig();
    private static volatile Set<Identifier> additionalOreIds = Set.of();
    private static volatile Set<Identifier> excludedBlockIds = Set.of();

    @Override
    public void onInitialize() {
        reloadConfig();
        LOGGER.info("Ore Glow loaded with light level {}.", config.clampedLightLevel());
    }

    public static OreGlowConfig config() {
        return config;
    }

    public static boolean isAdditionalOre(Block block) {
        return additionalOreIds.contains(Registries.BLOCK.getId(block));
    }

    public static boolean isExcluded(Block block) {
        return excludedBlockIds.contains(Registries.BLOCK.getId(block));
    }

    private static void reloadConfig() {
        OreGlowConfig loaded = readOrCreateConfig();
        config = loaded;
        additionalOreIds = OreGlowIds.parseIdSet(loaded.additionalOreIds, "additionalOreIds");
        excludedBlockIds = OreGlowIds.parseIdSet(loaded.excludedBlockIds, "excludedBlockIds");
    }

    private static OreGlowConfig readOrCreateConfig() {
        if (Files.notExists(CONFIG_PATH)) {
            OreGlowConfig defaults = new OreGlowConfig();
            writeConfig(defaults);
            return defaults;
        }

        try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
            OreGlowConfig loaded = GSON.fromJson(reader, OreGlowConfig.class);
            if (loaded != null) {
                return loaded;
            }
        } catch (IOException | RuntimeException exception) {
            LOGGER.warn("Failed to read {}, using defaults.", CONFIG_PATH, exception);
        }

        return new OreGlowConfig();
    }

    private static void writeConfig(OreGlowConfig config) {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(config, writer);
            }
        } catch (IOException exception) {
            LOGGER.warn("Failed to write default config to {}.", CONFIG_PATH, exception);
        }
    }
}
