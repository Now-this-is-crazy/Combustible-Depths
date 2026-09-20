package dev.powercyphe.combustible_depths.common.datagen.provider.feature;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import dev.powercyphe.combustible_depths.common.datagen.provider.CDDynamicRegistryProvider;
import dev.powercyphe.combustible_depths.common.registry.CDFeatures;

import java.util.concurrent.CompletableFuture;

public class CDFeatureProvider extends CDDynamicRegistryProvider<Feature> {
    public CDFeatureProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void configure() {
        register(CDFeatures.ORE_IGNITE);
        register(CDFeatures.ORE_IGNITE_SCATTERED);

        register(CDFeatures.ORE_SOUL_IGNITE);
        register(CDFeatures.ORE_SOUL_IGNITE_SCATTERED);
    }

    @Override
    public ResourceKey<Registry<Feature>> registry() {
        return Registries.FEATURE;
    }
}
