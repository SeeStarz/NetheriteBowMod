package com.seestarz.netherite_bow_mod.common.entity;

import com.seestarz.netherite_bow_mod.NetheriteBowMod;
import com.seestarz.netherite_bow_mod.common.entity.custom.ArrowEntity;
import com.seestarz.netherite_bow_mod.common.entity.custom.SpectralArrowEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
            NetheriteBowMod.MOD_ID);

    public static final RegistryObject<EntityType<ArrowEntity>> ARROW = ENTITIES.register("arrow",
            () -> EntityType.Builder.<ArrowEntity>create(ArrowEntity::new, EntityClassification.MISC).size(0.5F,
                    0.5F).trackingRange(4).setShouldReceiveVelocityUpdates(false).build(
                            new ResourceLocation(NetheriteBowMod.MOD_ID, "arrow").toString()));

    public static final RegistryObject<EntityType<SpectralArrowEntity>> SPECTRAL_ARROW = ENTITIES.register("spectral_arrow",
            () -> EntityType.Builder.<SpectralArrowEntity>create(SpectralArrowEntity::new, EntityClassification.MISC).size(0.5F,
                    0.5F).trackingRange(4).setShouldReceiveVelocityUpdates(false).build(
                            new ResourceLocation(NetheriteBowMod.MOD_ID, "spectral_arrow").toString()));

    public static void register(IEventBus eventBus) {ENTITIES.register(eventBus);}
}
