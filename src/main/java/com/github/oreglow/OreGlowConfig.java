package com.github.oreglow;

import java.util.List;

public final class OreGlowConfig {
    public int lightLevel = 9;
    public boolean respectHigherExistingLight = true;
    public boolean matchConventionalOreTags = true;
    public boolean matchBlockIdHeuristics = true;
    public List<String> additionalOreIds = List.of();
    public List<String> excludedBlockIds = List.of();

    public int clampedLightLevel() {
        return Math.max(0, Math.min(15, lightLevel));
    }
}
