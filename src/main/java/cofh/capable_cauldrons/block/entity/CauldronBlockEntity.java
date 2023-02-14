package cofh.capable_cauldrons.block.entity;

import cofh.capable_cauldrons.capabilities.CauldronTank;
import cofh.capable_cauldrons.init.BlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class CauldronBlockEntity extends BlockEntity {

    protected final CauldronTank fluidTank = new CauldronTank(this);

    public CauldronBlockEntity(BlockPos blockPos, BlockState state) {

        super(BlockEntityTypes.CAULDRON_TILE.get(), blockPos, state);
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

        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (!fluidCap.isPresent()) {
                fluidCap = LazyOptional.of(() -> fluidTank);
            }
            return fluidCap.cast();
        }
        return LazyOptional.empty();
    }
    // endregion
}
