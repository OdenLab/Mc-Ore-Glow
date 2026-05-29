package com.github.oreglow;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Locale;

public final class OreGlowMatcher {
    private static final List<TagKey<Block>> ORE_TAGS = List.of(
            blockTag("c", "ores"),
            blockTag("forge", "ores")
    );

    private OreGlowMatcher() {
    }

    public static boolean shouldGlow(BlockState state) {
        Block block = state.getBlock();
        OreGlowConfig config = OreGlowMod.config();

        if (config.clampedLightLevel() <= 0 || OreGlowMod.isExcluded(block)) {
            return false;
        }

        if (OreGlowMod.isAdditionalOre(block)) {
            return true;
        }

        if (config.matchConventionalOreTags && isInConventionalOreTag(state)) {
            return true;
        }

        return config.matchBlockIdHeuristics && looksLikeOre(block);
    }

    private static boolean isInConventionalOreTag(BlockState state) {
        for (TagKey<Block> tag : ORE_TAGS) {
            if (state.isIn(tag)) {
                return true;
            }
        }
        return false;
    }

    private static boolean looksLikeOre(Block block) {
        Identifier id = net.minecraft.registry.Registries.BLOCK.getId(block);
        String path = id.getPath().toLowerCase(Locale.ROOT);

        return path.equals("ancient_debris")
                || path.endsWith("_ore")
                || path.contains("_ore_")
                || path.startsWith("ore_")
                || path.equals("ore");
    }

    private static TagKey<Block> blockTag(String namespace, String path) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(namespace, path));
    }
}
