package com.drtdrc.mineablebedrock.mixins;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;
import java.util.function.Function;

@Mixin(Blocks.class)
public abstract class BedrockBlockMixin {

    @Redirect(
            method = "register(Lnet/minecraft/registry/RegistryKey;Ljava/util/function/Function;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;",
            at = @At(value = "RETURN")
    )
    private static Block onBlockRegistrationInBlocks(RegistryKey<Block> key, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        if (!"minecraft:bedrock".equals(key.getValue().toString())) {
            return Blocks.register(key, factory, settings);
        } else {
            return Blocks.register(key, AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(100f, 3600000.0f).requiresTool().allowsSpawning(Blocks::never));
        }
    }

}


//    public static final Block BEDROCK = Blocks.register("bedrock", AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0f, 3600000.0f).dropsNothing().allowsSpawning(Blocks::never));