package com.seestarz.netherite_bow_mod;

import com.seestarz.netherite_bow_mod.common.item.ModItems;
import com.seestarz.netherite_bow_mod.core.config.NetheriteBowConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NetheriteBowMod.MOD_ID)
public class NetheriteBowMod
{
    public static final String MOD_ID = "netherite_bow_mod";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public NetheriteBowMod() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, NetheriteBowConfig.SPEC, MOD_ID+"-common.toml");

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
}
