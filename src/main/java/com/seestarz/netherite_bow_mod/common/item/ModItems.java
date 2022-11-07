package com.seestarz.netherite_bow_mod.common.item;

import com.seestarz.netherite_bow_mod.NetheriteBowMod;
import com.seestarz.netherite_bow_mod.common.item.custom.NetheriteBowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NetheriteBowMod.MOD_ID);

    public static final RegistryObject<Item> NETHERITE_BOW = ITEMS.register("netherite_bow",
            () -> new NetheriteBowItem(new Item.Properties().group(ItemGroup.COMBAT).maxDamage(2031)));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }

}
