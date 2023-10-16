package cofh.capablecauldrons.mixin;

import cofh.capablecauldrons.block.entity.CauldronBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin (PistonBaseBlock.class)
public abstract class PistonBaseBlockMixin {

    @Inject (
            method = "isPushable",
            at = @At (
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;hasBlockEntity()Z"
            ),
            cancellable = true
    )
    private static void isCauldronOrComposter(BlockState state, Level level, BlockPos pos, Direction dir, boolean destroy, Direction dir2, CallbackInfoReturnable<Boolean> callback) {

        BlockEntity tile = level.getBlockEntity(pos);
        if (tile instanceof CauldronBlockEntity) {
            callback.setReturnValue(true);
        }
    }

}
