package cofh.capable_cauldrons.capabilities;

import cofh.capable_cauldrons.block.entity.CauldronBlockEntity;
import cofh.capable_cauldrons.util.CauldronUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public class CauldronTank implements IFluidHandler {

    protected final CauldronBlockEntity blockEntity;

    public CauldronTank(CauldronBlockEntity blockEntity) {

        this.blockEntity = blockEntity;
    }

    // region IFluidHandler
    @Override
    public int getTanks() {

        return 1;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {

        if (blockEntity.isRemoved()) {
            return FluidStack.EMPTY;
        }
        Fluid fluid = CauldronUtils.getFluidForCauldron(blockEntity.getBlockState());
        if (fluid != null) {
            return new FluidStack(fluid, getTankCapacity(0));
        } else {
            return FluidStack.EMPTY;
        }
    }

    @Override
    public int getTankCapacity(int tank) {

        return FluidAttributes.BUCKET_VOLUME;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {

        return CauldronUtils.getCauldronForFluid(stack) != null;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {

        if (blockEntity.isRemoved() || resource.isEmpty() || !getFluidInTank(0).isEmpty() || !isFluidValid(0, resource)) {
            return 0;
        }
        FluidStack fluid = getFluidInTank(0);
        if (fluid.isEmpty() || !fluid.isFluidEqual(resource)) {
            int capacity = getTankCapacity(0);
            if (resource.getAmount() >= capacity) {
                if (action.execute()) {
                    blockEntity.replaceBlockAndUpdate(CauldronUtils.getCauldronForFluid(resource));
                }
                return capacity;
            }
        }
        return 0;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {

        if (blockEntity.isRemoved() || resource.isEmpty() || !getFluidInTank(0).isFluidEqual(resource)) {
            return FluidStack.EMPTY;
        } else {
            return drain(resource.getAmount(), action);
        }
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction action) {

        if (blockEntity.isRemoved()) {
            return FluidStack.EMPTY;
        }
        FluidStack fluid = getFluidInTank(0);
        if (maxDrain >= fluid.getAmount()) {
            if (action.execute()) {
                blockEntity.replaceBlockAndUpdate(Blocks.CAULDRON.defaultBlockState());
            }
            return fluid.copy();
        } else {
            return FluidStack.EMPTY;
        }
    }
    // endregion
}
