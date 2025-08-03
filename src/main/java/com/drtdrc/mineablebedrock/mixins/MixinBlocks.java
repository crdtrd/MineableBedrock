package com.drtdrc.mineablebedrock.mixins;

import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Blocks.class)
public class MixinBlocks {
    @Redirect(at = @At())
}
