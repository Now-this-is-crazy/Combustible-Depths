package powercyphe.combustible_depths.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import powercyphe.combustible_depths.client.particle.IgniteExplosionParticle;
import powercyphe.combustible_depths.client.particle.IgniteShardParticle;
import powercyphe.combustible_depths.client.render.PrimedIgniteEntityRenderer;
import powercyphe.combustible_depths.common.payload.IgniteExplosionPayload;
import powercyphe.combustible_depths.common.registry.CDEntities;
import powercyphe.combustible_depths.common.registry.CDParticles;

public class CombustibleDepthsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRenderers.register(CDEntities.PRIMED_IGNITE, PrimedIgniteEntityRenderer::new);

        ParticleProviderRegistry.getInstance().register(CDParticles.IGNITE_EXPLOSION, IgniteExplosionParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(CDParticles.SOUL_IGNITE_EXPLOSION, IgniteExplosionParticle.Provider::new);

        ParticleProviderRegistry.getInstance().register(CDParticles.IGNITE_SHARD, IgniteShardParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(CDParticles.SOUL_IGNITE_SHARD, IgniteShardParticle.Provider::new);

        ClientPlayNetworking.registerGlobalReceiver(IgniteExplosionPayload.TYPE, new IgniteExplosionPayload.Receiver());
    }
}
