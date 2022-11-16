package com.seestarz.netherite_bow_mod.core.network.message;

import com.seestarz.netherite_bow_mod.NetheriteBowMod;
import com.seestarz.netherite_bow_mod.core.config.NetheriteBowCommonConfig;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ConfigMessage {
    public double chargeTime;
    public double velocityMultiplier;

    public ConfigMessage(double chargeTime, double velocityMultiplier){
        this.chargeTime = chargeTime;
        this.velocityMultiplier = velocityMultiplier;
    }

    public static void encode(ConfigMessage message, PacketBuffer buffer) {
        buffer.writeDouble(message.chargeTime);
        buffer.writeDouble(message.velocityMultiplier);
    }

    public static ConfigMessage decode(PacketBuffer buffer) {
        double chargeTime = buffer.readDouble();
        double velocityMultiplier = buffer.readDouble();
        return new ConfigMessage(chargeTime, velocityMultiplier);
    }

    public static void handle(ConfigMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (context.getSender() != null)
            {
                // Player attempted to change server config
                NetheriteBowMod.LOGGER.warn("Invalid config sync attempt from player: " + context.getSender().getName());
                return;
            }
            NetheriteBowCommonConfig.chargeTime.set(message.chargeTime);
            NetheriteBowCommonConfig.velocityMultiplier.set(message.velocityMultiplier);
        });
        context.setPacketHandled(true);
    }
}
