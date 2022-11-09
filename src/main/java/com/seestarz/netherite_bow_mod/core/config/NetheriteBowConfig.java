package com.seestarz.netherite_bow_mod.core.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class NetheriteBowConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Float> chargeTime;
    public static final ForgeConfigSpec.ConfigValue<Float> velocityMultiplier;
    public static final ForgeConfigSpec.ConfigValue<Float> maxZoom;

    static {
        BUILDER.push("Config for Netherite Bow Mod");

        chargeTime = BUILDER.comment("Charging time of Netherite Bow in ticks (20 ticks is 1 second, vanilla bow is " +
                "20 ticks). Default value is 60.0").define("Charge Time", 60f);
        velocityMultiplier = BUILDER.comment("Velocity multiplier of Netherite Bow compared to vanilla bow. " +
                "Default value is 4.0").define("Velocity Multiplier", 4f);
        maxZoom = BUILDER.comment("Max zoom of Netherite Bow (vanilla bow is 0.15f). Default value is 0.3").define(
                "Max Zoom", 0.3f);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
