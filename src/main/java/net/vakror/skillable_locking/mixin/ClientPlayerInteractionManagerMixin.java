package net.vakror.skillable_locking.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.vakror.skillable_locking.locking.LockingUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    public void preventInteractingBlock(ClientPlayerEntity player, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        if (!LockingUtils.canUseBlock(player, player.world.getBlockState(hitResult.getBlockPos()).getBlock())); {
            cir.setReturnValue(ActionResult.FAIL);
        }
        cir.cancel();
    }

    @Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
    public void preventInteractingItem(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (!LockingUtils.canUseItem(player, player.getActiveItem().getItem())) {
            cir.setReturnValue(ActionResult.FAIL);
        }
        cir.cancel();
    }
}
