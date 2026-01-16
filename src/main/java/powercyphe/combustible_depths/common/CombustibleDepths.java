package powercyphe.combustible_depths.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.resources.ResourceLocation;
import powercyphe.combustible_depths.common.payload.IgniteExplosionPayload;
import powercyphe.combustible_depths.common.registry.*;

public class CombustibleDepths implements ModInitializer {
    public static final String MOD_ID = "combustible_depths";

    @Override
    public void onInitialize() {
        CDBlocks.init();
        CDEntities.init();
        CDSounds.init();
        CDFeatures.init();
        CDParticles.init();

        PayloadTypeRegistry.playS2C().register(IgniteExplosionPayload.TYPE, IgniteExplosionPayload.CODEC);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.tryBuild(MOD_ID, path);
    }
}
