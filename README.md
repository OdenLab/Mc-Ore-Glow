# Ore Glow

A Fabric 1.20.1 mod that makes vanilla and modded ores emit light.

## Features

- Makes ore blocks glow by changing their block luminance at runtime.
- Works with vanilla ores and most modded ores by checking:
  - conventional `c:ores` and `forge:ores` block tags;
  - block ids that look like ores, such as `ruby_ore`, `deepslate_tin_ore`, or `ore_copper`;
  - a configurable manual allow-list.
- Keeps compatibility high by avoiding hard dependencies on Fabric API or specific ore mods.
- Supports exclusions for false positives.

## Configuration

On first launch, the mod creates `config/oreglow.json`:

```json
{
  "lightLevel": 9,
  "respectHigherExistingLight": true,
  "matchConventionalOreTags": true,
  "matchBlockIdHeuristics": true,
  "additionalOreIds": [],
  "excludedBlockIds": []
}
```

`lightLevel` is clamped to Minecraft's valid light range of `0` to `15`.
Use `additionalOreIds` for unusual ore block ids and `excludedBlockIds` for blocks that should not glow.

## Build

```bash
gradle build
```

The mod jar is written to `build/libs/`.
