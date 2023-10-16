package cofh.capablecauldrons;

import cofh.capablecauldrons.init.BlockEntityTypes;
import cofh.capablecauldrons.util.CauldronUtils;
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

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);

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
