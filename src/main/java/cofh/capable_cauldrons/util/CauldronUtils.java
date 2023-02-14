package cofh.capable_cauldrons.util;

import cofh.capable_cauldrons.block.entity.CauldronBlockEntity;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

public class CauldronUtils {

    protected static BiMap<Fluid, BlockState> CAULDRON_MAP = HashBiMap.create();

    private CauldronUtils() {

    }

    public static void setup() {

        addMapping(Fluids.WATER, Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, LayeredCauldronBlock.MAX_FILL_LEVEL));
        addMapping(Fluids.LAVA, Blocks.LAVA_CAULDRON.defaultBlockState());
    }

    public static void addMapping(Fluid fluid, BlockState state) {

        CAULDRON_MAP.put(fluid, state);
    }

    public static BlockState getCauldronForFluid(FluidStack fluid) {

        return CAULDRON_MAP.get(fluid.getFluid());
    }

    public static BlockState getCauldronForFluid(Fluid fluid) {

        return CAULDRON_MAP.get(fluid);
    }

    public static Fluid getFluidForCauldron(BlockState state) {

        return CAULDRON_MAP.inverse().get(state);
    }

    // region DISPENSER LOGIC
    public static DispenseItemBehavior dispenseBucket(BucketItem bucket, DispenseItemBehavior defaultReturn) {

        if (bucket == Items.BUCKET) {
            return fillBucket(defaultReturn);
        }
        return emptyBucket(bucket.getFluid(), defaultReturn);
    }

    public static DispenseItemBehavior fillBucket(DispenseItemBehavior defaultReturn) {

        return (source, item) -> {

            Level level = source.getLevel();
            BlockPos pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));

            if (level.getBlockEntity(pos) instanceof CauldronBlockEntity blockEntity) {
                Fluid fluid = getFluidForCauldron(blockEntity.getBlockState());
                if (fluid != null) {
                    blockEntity.replaceBlockAndUpdate(Blocks.CAULDRON.defaultBlockState());
                    level.levelEvent(LevelEvent.SOUND_DISPENSER_DISPENSE, source.getPos(), 0);
                    return new ItemStack(fluid.getBucket());
                }
            }
            return defaultReturn.dispense(source, item);
        };
    }

    public static DispenseItemBehavior emptyBucket(Fluid fluid, DispenseItemBehavior defaultReturn) {

        return (source, item) -> {

            Level level = source.getLevel();
            BlockPos pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));

            if (level.getBlockEntity(pos) instanceof CauldronBlockEntity blockEntity) {
                BlockState state = getCauldronForFluid(fluid);
                if (state != null) {
                    blockEntity.replaceBlockAndUpdate(state);
                    level.levelEvent(LevelEvent.SOUND_DISPENSER_DISPENSE, source.getPos(), 0);
                    return new ItemStack(Items.BUCKET);
                }
            }
            return defaultReturn.dispense(source, item);
        };
    }
    // endregion
}
