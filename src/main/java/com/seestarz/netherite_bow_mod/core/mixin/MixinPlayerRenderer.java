package com.seestarz.netherite_bow_mod.core.mixin;

import com.seestarz.netherite_bow_mod.common.item.ModItems;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class MixinPlayerRenderer {
    @Inject(method = "Lnet/minecraft/client/renderer/entity/PlayerRenderer;func_241741_a_(Lnet/minecraft/client/entity/player/AbstractClientPlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/client/renderer/entity/model/BipedModel$ArmPose;",
            at = @At(value="INVOKE", target = "Lnet/minecraft/item/ItemStack;getUseAction()Lnet/minecraft/item/UseAction;", shift = At.Shift.AFTER), cancellable = true)
    private static void func_241741_a_(AbstractClientPlayerEntity p_241741_0_, Hand p_241741_1_, CallbackInfoReturnable<BipedModel.ArmPose> cir) {
        ItemStack itemstack = p_241741_0_.getHeldItem(p_241741_1_);
        if (itemstack.getItem() == ModItems.NETHERITE_BOW.get()) {
            cir.setReturnValue(BipedModel.ArmPose.BOW_AND_ARROW);
            cir.cancel();
        }
    }
}
