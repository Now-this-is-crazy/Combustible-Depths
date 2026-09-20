package dev.powercyphe.combustible_depths.common.datagen.provider.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemIds;
import net.minecraft.references.ItemIds;
import dev.powercyphe.combustible_depths.common.registry.CDTags;

import java.util.concurrent.CompletableFuture;

public class CDItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
    public CDItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture, CDBlockTagProvider blockTagProvider) {
        super(output, registryLookupFuture, blockTagProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        copy(CDTags.Blocks.IGNITE, CDTags.Items.IGNITE);

        builder(CDTags.Items.IGNITES_IGNITE)
                .add(ItemIds.FLINT_AND_STEEL)
                .add(ItemIds.FIRE_CHARGE);

        builder(CDTags.Items.IGNITE_CRAFTING_MATERIALS)
                .add(ItemIds.FLINT);
        builder(CDTags.Items.SOUL_IGNITE_CRAFTING_MATERIALS)
                .add(BlockItemIds.SOUL_SAND)
                .add(BlockItemIds.SOUL_SOIL);
    }
}
