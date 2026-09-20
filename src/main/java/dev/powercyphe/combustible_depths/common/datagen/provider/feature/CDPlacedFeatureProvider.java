package dev.powercyphe.combustible_depths.common.datagen.provider.feature;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import dev.powercyphe.combustible_depths.common.datagen.provider.CDDynamicRegistryProvider;
import dev.powercyphe.combustible_depths.common.registry.CDPlacedFeatures;

import java.util.concurrent.CompletableFuture;

public class CDPlacedFeatureProvider extends CDDynamicRegistryProvider<PlacedFeature> {
    public CDPlacedFeatureProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void configure() {
        register(CDPlacedFeatures.ORE_IGNITE_LOWER);
        register(CDPlacedFeatures.ORE_IGNITE_UPPER);

        register(CDPlacedFeatures.ORE_SOUL_IGNITE_LOWER);
        register(CDPlacedFeatures.ORE_SOUL_IGNITE_UPPER);
    }

    @Override
    public ResourceKey<Registry<PlacedFeature>> registry() {
        return Registries.PLACED_FEATURE;
    }
}
