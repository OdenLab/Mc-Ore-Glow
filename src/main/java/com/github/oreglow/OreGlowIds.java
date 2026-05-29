package com.github.oreglow;

import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class OreGlowIds {
    private OreGlowIds() {
    }

    public static Set<Identifier> parseIdSet(List<String> rawIds, String fieldName) {
        if (rawIds == null || rawIds.isEmpty()) {
            return Set.of();
        }

        Set<Identifier> ids = new HashSet<>();
        for (String rawId : rawIds) {
            Identifier id = Identifier.tryParse(rawId);
            if (id == null) {
                OreGlowMod.LOGGER.warn("Ignoring invalid block id '{}' in {}.", rawId, fieldName);
                continue;
            }
            ids.add(id);
        }
        return Set.copyOf(ids);
    }
}
