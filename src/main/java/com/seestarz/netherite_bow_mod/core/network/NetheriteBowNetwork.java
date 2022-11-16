package com.seestarz.netherite_bow_mod.core.network;

import com.seestarz.netherite_bow_mod.NetheriteBowMod;
import com.seestarz.netherite_bow_mod.core.network.message.ConfigMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetheriteBowNetwork {
    public static final String NETWORK_VERSION = "0.1.0";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(NetheriteBowMod.MOD_ID, "network"),
            () -> NETWORK_VERSION,
            version -> version.equals(NETWORK_VERSION),
            version -> version.equals(NETWORK_VERSION)
    );

    public static void init() {
        CHANNEL.registerMessage(0, ConfigMessage.class, ConfigMessage::encode, ConfigMessage::decode,
                ConfigMessage::handle);
    }
}
