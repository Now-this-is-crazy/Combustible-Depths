package dev.powercyphe.combustible_depths.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.resources.Identifier;
import dev.powercyphe.combustible_depths.common.payload.IgniteExplosionPayload;
import dev.powercyphe.combustible_depths.common.registry.*;

public class CombustibleDepths implements ModInitializer {
    public static final String MOD_ID = "combustible_depths";

    @Override
    public void onInitialize() {
        CDBlocks.init();
        CDEntities.init();
        CDSounds.init();
        CDFeatures.init();
        CDPlacedFeatures.init();
        CDParticles.init();
        CDTags.init();

        PayloadTypeRegistry.clientboundPlay().register(IgniteExplosionPayload.TYPE, IgniteExplosionPayload.CODEC);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
