package com.seestarz.netherite_bow_mod.core.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class NetheriteBowClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Double> maxZoom;

    static {
        BUILDER.push("Config for Netherite Bow Mod");

        maxZoom = BUILDER.comment("Max zoom of Netherite Bow (vanilla bow is 0.15). Default value is 0.3").define(
                "Max Zoom", 0.3D);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
