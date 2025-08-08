package com.drtdrc.mixin;


import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(Blocks.class)
public class BlocksMixin {

    // Hacking the bedrock registry to change hardness, allow drops, and require the mineable tool, in this case a pickaxe
    @Inject(
            method = "register(Lnet/minecraft/registry/RegistryKey;Ljava/util/function/Function;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void onRegister(RegistryKey<Block> key, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, CallbackInfoReturnable<Block> cir) {
        if (key.getValue().toString().contentEquals("minecraft:bedrock")) {

            AbstractBlock.Settings newBedrockSettings = AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(100.0f, 3600000.0f).allowsSpawning((state, world1, pos1, entityType) -> false).requiresTool();

            Block block = factory.apply(newBedrockSettings.registryKey(key));
            cir.setReturnValue(Registry.register(Registries.BLOCK, key, block));
        }
    }
}