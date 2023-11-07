package cofh.capablecauldrons.common.block.entity;

import cofh.capablecauldrons.common.capabilities.CauldronTank;
import cofh.capablecauldrons.init.registries.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

public class CauldronBlockEntity extends BlockEntity {

    protected final CauldronTank fluidTank = new CauldronTank(this);

    public CauldronBlockEntity(BlockPos blockPos, BlockState state) {

        super(ModBlockEntityTypes.CAULDRON_TILE.get(), blockPos, state);
    }

    public void replaceBlockAndUpdate(BlockState newBlockState) {

        if (level != null) {
            level.setBlockAndUpdate(worldPosition, newBlockState);
        }
    }

    @Override
    public void setRemoved() {

        super.setRemoved();
        fluidCap.invalidate();
    }

    // region CAPABILITIES
    protected LazyOptional<?> fluidCap = LazyOptional.empty();

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {

        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            if (!fluidCap.isPresent()) {
                fluidCap = LazyOptional.of(() -> fluidTank);
            }
            return fluidCap.cast();
        }
        return LazyOptional.empty();
    }
    // endregion
}
