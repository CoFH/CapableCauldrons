package cofh.capable_cauldrons;

import cofh.capable_cauldrons.init.BlockEntityTypes;
import cofh.capable_cauldrons.util.CauldronUtils;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod (CapableCauldrons.MOD_ID)
public class CapableCauldrons {

    public static final String MOD_ID = "capable_cauldrons";

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MOD_ID);

    public CapableCauldrons() {

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        BLOCK_ENTITY_TYPES.register(modEventBus);

        BlockEntityTypes.register();
    }

    public void commonSetup(FMLCommonSetupEvent event) {

        event.enqueueWork(CauldronUtils::setup);
    }

}
