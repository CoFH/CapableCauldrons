package cofh.capablecauldrons.mixin;

import cofh.capablecauldrons.block.entity.CauldronBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin (AbstractCauldronBlock.class)
public class AbstractCauldronBlockMixin implements EntityBlock {

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        return new CauldronBlockEntity(pos, state);
    }

}
