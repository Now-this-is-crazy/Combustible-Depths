package dev.powercyphe.combustible_depths.common.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.concurrent.CompletableFuture;

public abstract class CDDynamicRegistryProvider<T> extends FabricDynamicRegistryProvider {
    private HolderLookup.Provider registries;
    private Entries entries;

    public CDDynamicRegistryProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    public HolderLookup.Provider registries() {
        return this.registries;
    }

    @Override
    protected final void configure(HolderLookup.Provider registries, Entries entries) {
        this.registries = registries;
        this.entries = entries;

        this.configure();
    }

    public abstract void configure();

    public final void register(ResourceKey<T> key) {
        this.entries.add(this.registries.getOrThrow(key));
    }

    public abstract ResourceKey<Registry<T>> registry();

    @Override
    public String getName() {
        return this.registry().identifier().getPath();
    }
}
