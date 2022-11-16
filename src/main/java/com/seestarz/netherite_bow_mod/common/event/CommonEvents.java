package com.seestarz.netherite_bow_mod.common.event;

import com.seestarz.netherite_bow_mod.NetheriteBowMod;
import com.seestarz.netherite_bow_mod.common.item.ModItems;
import com.seestarz.netherite_bow_mod.common.item.custom.NetheriteBowItem;
import com.seestarz.netherite_bow_mod.core.config.NetheriteBowCommonConfig;
import com.seestarz.netherite_bow_mod.core.network.NetheriteBowNetwork;
import com.seestarz.netherite_bow_mod.core.network.message.ConfigMessage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = NetheriteBowMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class CommonEvents {

    @SubscribeEvent
    public static void onFOVUpdateEvent(FOVUpdateEvent event) {
        PlayerEntity player = event.getEntity();
        float f = event.getFov();

        if (player.getHeldItemMainhand().getItem() == ModItems.NETHERITE_BOW.get()) {
            int charge = player.getItemInUseMaxCount();
            float f1 = (float)charge / NetheriteBowItem.getChargeTime();
            if (f1 > 1.0F) {
                f1 = 1.0F;
            } else {
                f1 = f1 * f1;
            }

            float fovModifier = 1.0F - f1 * NetheriteBowItem.getMaxZoom();

            event.setNewfov(f * fovModifier);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        NetheriteBowNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new ConfigMessage(
                NetheriteBowCommonConfig.chargeTime.get(), NetheriteBowCommonConfig.velocityMultiplier.get()
        ));
    }

    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerRespawnEvent event) {
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        NetheriteBowNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new ConfigMessage(
                NetheriteBowCommonConfig.chargeTime.get(), NetheriteBowCommonConfig.velocityMultiplier.get()
        ));
    }

    @SubscribeEvent
    public static void onPlayerLoggedInEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        NetheriteBowNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new ConfigMessage(
                NetheriteBowCommonConfig.chargeTime.get(), NetheriteBowCommonConfig.velocityMultiplier.get()
        ));
    }
}
