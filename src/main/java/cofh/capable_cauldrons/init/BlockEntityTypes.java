package cofh.capable_cauldrons.init;

import cofh.capable_cauldrons.CapableCauldrons;
import cofh.capable_cauldrons.block.entity.CauldronBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.world.level.block.Blocks.*;

public class BlockEntityTypes {

    private BlockEntityTypes() {

    }

    public static void register() {

    }

    public static final RegistryObject<BlockEntityType<?>> CAULDRON_TILE = CapableCauldrons.BLOCK_ENTITY_TYPES.register("cauldron", () -> BlockEntityType.Builder.of(CauldronBlockEntity::new, CAULDRON, WATER_CAULDRON, LAVA_CAULDRON, POWDER_SNOW_CAULDRON).build(null));

}
