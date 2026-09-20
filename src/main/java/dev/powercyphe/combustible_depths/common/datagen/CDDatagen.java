package dev.powercyphe.combustible_depths.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import dev.powercyphe.combustible_depths.common.datagen.bootstrap.CDFeatureBootstrap;
import dev.powercyphe.combustible_depths.common.datagen.provider.CDBlockLootProvider;
import dev.powercyphe.combustible_depths.common.datagen.provider.CDRecipeProvider;
import dev.powercyphe.combustible_depths.common.datagen.provider.feature.CDFeatureProvider;
import dev.powercyphe.combustible_depths.common.datagen.provider.feature.CDPlacedFeatureProvider;
import dev.powercyphe.combustible_depths.common.datagen.provider.tag.CDBlockTagProvider;
import dev.powercyphe.combustible_depths.common.datagen.provider.tag.CDItemTagProvider;

public class CDDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        CDBlockTagProvider blockTagProvider = pack.addProvider(CDBlockTagProvider::new);
        pack.addProvider((output, future) -> new CDItemTagProvider(output, future, blockTagProvider));

        pack.addProvider(CDFeatureProvider::new);
        pack.addProvider(CDPlacedFeatureProvider::new);

        pack.addProvider(CDRecipeProvider::new);
        pack.addProvider(CDBlockLootProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(new CDFeatureBootstrap());
    }
}
