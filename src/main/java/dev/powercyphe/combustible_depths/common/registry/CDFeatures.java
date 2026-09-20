package dev.powercyphe.combustible_depths.common.registry;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import dev.powercyphe.combustible_depths.common.CombustibleDepths;

public interface CDFeatures {


    ResourceKey<Feature> ORE_IGNITE = register("ore_ignite");
    ResourceKey<Feature> ORE_IGNITE_SCATTERED = register("ore_ignite_scattered");

    ResourceKey<Feature> ORE_SOUL_IGNITE = register("ore_soul_ignite");
    ResourceKey<Feature> ORE_SOUL_IGNITE_SCATTERED = register("ore_soul_ignite_scattered");

    static void init() {}

    static <T extends Feature> MapCodec<T> register(String name, MapCodec<T> codec) {
        return Registry.register(BuiltInRegistries.FEATURE_TYPE, CombustibleDepths.id(name), codec);
    }

    static ResourceKey<Feature> register(String name) {
        return ResourceKey.create(Registries.FEATURE, CombustibleDepths.id(name));
    }

}
