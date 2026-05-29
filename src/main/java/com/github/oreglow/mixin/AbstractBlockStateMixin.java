package com.github.oreglow.mixin;

import com.github.oreglow.OreGlowMatcher;
import com.github.oreglow.OreGlowMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {
    @Shadow
    public abstract BlockState asBlockState();

    @Inject(method = "getLuminance", at = @At("RETURN"), cancellable = true)
    private void oreglow$makeOresGlow(CallbackInfoReturnable<Integer> cir) {
        int configuredLight = OreGlowMod.config().clampedLightLevel();
        if (OreGlowMod.config().respectHigherExistingLight && cir.getReturnValueI() >= configuredLight) {
            return;
        }

        if (OreGlowMatcher.shouldGlow(asBlockState())) {
            cir.setReturnValue(configuredLight);
        }
    }
}
