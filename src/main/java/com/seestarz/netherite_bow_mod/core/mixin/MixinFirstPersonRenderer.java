package com.seestarz.netherite_bow_mod.core.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.seestarz.netherite_bow_mod.common.item.ModItems;
import com.seestarz.netherite_bow_mod.common.item.custom.NetheriteBowItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.FirstPersonRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FirstPersonRenderer.class)
public abstract class MixinFirstPersonRenderer {
    @Inject(method = "Lnet/minecraft/client/renderer/FirstPersonRenderer;renderItemInFirstPerson(Lnet/minecraft/client/entity/player/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/FirstPersonRenderer;renderItemSide(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/model/ItemCameraTransforms$TransformType;ZLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;I)V"))
    private void renderItemInFirstPerson(AbstractClientPlayerEntity player, float partialTicks, float pitch,
                                         Hand handIn, float swingProgress, ItemStack stack, float equippedProgress,
                                         MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn,
                                         CallbackInfo callback) {

        // Check if held item is netherite bow
        if (player.isHandActive() && player.getItemInUseCount() > 0 && player.getActiveHand() == handIn) {
            if (stack.getItem() == ModItems.NETHERITE_BOW.get()) {
                boolean flag = handIn == Hand.MAIN_HAND;
                HandSide handside = flag ? player.getPrimaryHand() : player.getPrimaryHand().opposite();
                boolean flag3 = handside == HandSide.RIGHT;
                int k = flag3 ? 1 : -1;

                // Minecraft bow position render
                matrixStackIn.translate((double) ((float) k * -0.2785682F), (double) 0.18344387F, (double) 0.15731531F);
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(-13.935F));
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees((float) k * 35.3F));
                matrixStackIn.rotate(Vector3f.ZP.rotationDegrees((float) k * -9.785F));
                float f8 = (float) stack.getUseDuration() - ((float) this.mc.player.getItemInUseCount() - partialTicks + 1.0F);

                // Change how fast position changes
                float f12 = f8 / NetheriteBowItem.chargeTime;

                f12 = (f12 * f12 + f12 * 2.0F) / 3.0F;
                if (f12 > 1.0F) {
                    f12 = 1.0F;
                }

                if (f12 > 0.1F) {
                    float f15 = MathHelper.sin((f8 - 0.1F) * 1.3F);
                    float f18 = f12 - 0.1F;
                    float f20 = f15 * f18;
                    matrixStackIn.translate((double) (f20 * 0.0F), (double) (f20 * 0.004F), (double) (f20 * 0.0F));
                }

                matrixStackIn.translate((double) (f12 * 0.0F), (double) (f12 * 0.0F), (double) (f12 * 0.04F));
                matrixStackIn.scale(1.0F, 1.0F, 1.0F + f12 * 0.2F);
                matrixStackIn.rotate(Vector3f.YN.rotationDegrees((float) k * 45.0F));
            }
        }
    }

    @Final
    @Shadow
    private Minecraft mc;
}