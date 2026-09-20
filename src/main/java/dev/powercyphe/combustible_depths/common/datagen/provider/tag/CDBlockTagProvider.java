package dev.powercyphe.combustible_depths.common.datagen.provider.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.BlockTags;
import dev.powercyphe.combustible_depths.common.registry.CDBlocks;

import java.util.concurrent.CompletableFuture;

import static dev.powercyphe.combustible_depths.common.registry.CDTags.Blocks.*;

public class CDBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
    public CDBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        builder(IGNITE)
                .add(CDBlocks.IGNITE_ID)
                .add(CDBlocks.SOUL_IGNITE_ID);

        builder(IGNITE_REPLACEABLE)
                .forceAddTag(BlockTags.BASE_STONE_NETHER);

        builder(SOUL_IGNITE_REPLACEABLE)
                .addTag(IGNITE_REPLACEABLE)
                .add(BlockItemIds.SOUL_SAND)
                .add(BlockItemIds.SOUL_SOIL);

    }
}
