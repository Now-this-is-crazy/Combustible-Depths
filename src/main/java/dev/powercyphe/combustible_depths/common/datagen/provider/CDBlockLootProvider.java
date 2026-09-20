package dev.powercyphe.combustible_depths.common.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import dev.powercyphe.combustible_depths.common.registry.CDBlocks;

import java.util.concurrent.CompletableFuture;

public class CDBlockLootProvider extends FabricBlockLootSubProvider {
    public CDBlockLootProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate() {
        dropSelf(CDBlocks.IGNITE);
        dropSelf(CDBlocks.SOUL_IGNITE);
    }
}
