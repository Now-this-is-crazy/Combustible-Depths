package dev.powercyphe.combustible_depths.common.registry;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import dev.powercyphe.combustible_depths.common.CombustibleDepths;

import java.util.function.Predicate;

public interface CDPlacedFeatures {

    ResourceKey<PlacedFeature> ORE_IGNITE_LOWER = register("ore_ignite_lower");
    ResourceKey<PlacedFeature> ORE_IGNITE_UPPER = register("ore_ignite_upper");

    ResourceKey<PlacedFeature> ORE_SOUL_IGNITE_LOWER = register("ore_soul_ignite_lower");
    ResourceKey<PlacedFeature> ORE_SOUL_IGNITE_UPPER = register("ore_soul_ignite_upper");

    Predicate<BiomeSelectionContext> NETHER_NON_SOUL_SAND_VALLEY = BiomeSelectors.foundInTheNether().and(BiomeSelectors.excludeByKey(Biomes.SOUL_SAND_VALLEY));
    Predicate<BiomeSelectionContext> NETHER_SOUL_SAND_VALLEY = BiomeSelectors.foundInTheNether().and(BiomeSelectors.includeByKey(Biomes.SOUL_SAND_VALLEY));

    static void init() {
        BiomeModifications.addFeature(NETHER_NON_SOUL_SAND_VALLEY,
                GenerationStep.Decoration.UNDERGROUND_DECORATION, ORE_IGNITE_LOWER);
        BiomeModifications.addFeature(NETHER_NON_SOUL_SAND_VALLEY,
                GenerationStep.Decoration.UNDERGROUND_DECORATION, ORE_IGNITE_UPPER);

        BiomeModifications.addFeature(NETHER_SOUL_SAND_VALLEY,
                GenerationStep.Decoration.UNDERGROUND_DECORATION, ORE_SOUL_IGNITE_LOWER);
        BiomeModifications.addFeature(NETHER_SOUL_SAND_VALLEY,
                GenerationStep.Decoration.UNDERGROUND_DECORATION, ORE_SOUL_IGNITE_UPPER);
    }

    static ResourceKey<PlacedFeature> register(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, CombustibleDepths.id(id));
    }
}
