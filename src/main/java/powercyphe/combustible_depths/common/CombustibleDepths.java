package powercyphe.combustible_depths.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.resources.Identifier;
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

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
