package cofh.capablecauldrons.init.registries;

import cofh.capablecauldrons.CapableCauldrons;
import cofh.capablecauldrons.common.block.entity.CauldronBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.world.level.block.Blocks.*;

public class ModBlockEntityTypes {

    private ModBlockEntityTypes() {

    }

    public static void register() {

    }

    public static final RegistryObject<BlockEntityType<?>> CAULDRON_TILE = CapableCauldrons.BLOCK_ENTITY_TYPES.register("cauldron", () -> BlockEntityType.Builder.of(CauldronBlockEntity::new, CAULDRON, WATER_CAULDRON, LAVA_CAULDRON, POWDER_SNOW_CAULDRON).build(null));

}
