package com.drtdrc.mixin;

import net.minecraft.network.packet.s2c.play.BlockBreakingProgressS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Shadow public abstract List<ServerPlayerEntity> getPlayers();

    // Allows breaking player to see breaking progress
    @Inject(
            method = "setBlockBreakingInfo(ILnet/minecraft/util/math/BlockPos;I)V",
            at = @At("RETURN")
    )
    private void onSetBlockBreakingInfo(
            int breakerId,
            BlockPos pos,
            int stage,
            CallbackInfo ci
    ) {
        for (ServerPlayerEntity p : this.getPlayers()) {
            if (p.getId() == breakerId) {
                p.networkHandler.sendPacket(new BlockBreakingProgressS2CPacket(breakerId, pos, stage));
                break;
            }
        }
    }


}
