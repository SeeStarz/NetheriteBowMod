package com.seestarz.netherite_bow_mod;

import com.seestarz.netherite_bow_mod.common.entity.ModEntityType;
import com.seestarz.netherite_bow_mod.common.entity.custom.SpectralArrowRenderer;
import com.seestarz.netherite_bow_mod.common.entity.custom.TippedArrowRenderer;
import com.seestarz.netherite_bow_mod.common.item.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
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
        ModEntityType.register(eventBus);

        eventBus.addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.ARROW.get(), TippedArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityType.SPECTRAL_ARROW.get(),
                SpectralArrowRenderer::new);
    }
}
