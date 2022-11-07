package com.seestarz.netherite_bow_mod.common.event;

import com.seestarz.netherite_bow_mod.NetheriteBowMod;
import com.seestarz.netherite_bow_mod.common.item.ModItems;
import com.seestarz.netherite_bow_mod.common.item.custom.NetheriteBowItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NetheriteBowMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class CommonEvents {

    @SubscribeEvent
    public static void onFOVUpdateEvent(FOVUpdateEvent event) {
        PlayerEntity player = event.getEntity();
        float f = event.getFov();

        if (player.getHeldItemMainhand().getItem() == ModItems.NETHERITE_BOW.get()) {
            int charge = player.getItemInUseMaxCount();
            float f1 = (float)charge * NetheriteBowItem.chargeTime / 20f;
            if (f1 > 1.0F) {
                f1 = 1.0F;
            } else {
                f1 = f1 * f1;
            }

            float fovModifier = 1.0F - f1 * 0.3F;

            event.setNewfov(f * fovModifier);
        }
    }
}
