package cofh.capable_cauldrons.mixin;

import cofh.capable_cauldrons.util.CauldronUtils;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin (DispenserBlock.class)
public abstract class DispenserBlockMixin {

    @Inject (method = "getDispenseMethod", at = @At ("TAIL"), cancellable = true)
    private void getDispenseMethod(ItemStack stack, CallbackInfoReturnable<DispenseItemBehavior> callback) {

        if (stack.getItem() instanceof BucketItem bucket) {
            DispenseItemBehavior defaultReturn = callback.getReturnValue();
            callback.setReturnValue(CauldronUtils.dispenseBucket(bucket, defaultReturn));
        }
    }

}
