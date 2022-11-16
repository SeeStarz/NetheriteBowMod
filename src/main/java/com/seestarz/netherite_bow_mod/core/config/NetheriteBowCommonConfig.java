package com.seestarz.netherite_bow_mod.core.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class NetheriteBowCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Double> chargeTime;
    public static final ForgeConfigSpec.ConfigValue<Double> velocityMultiplier;

    static {
        BUILDER.push("Config for Netherite Bow Mod");

        chargeTime = BUILDER.comment("Charging time of Netherite Bow in ticks (20 ticks is 1 second, vanilla bow is " +
                "20 ticks). Default value is 60.0").define("Charge Time", 60D);
        velocityMultiplier = BUILDER.comment("Velocity multiplier of Netherite Bow compared to vanilla bow. " +
                "Default value is 4.0").define("Velocity Multiplier", 4D);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
