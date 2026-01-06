package powercyphe.combustible_depths.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadAtlas;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import powercyphe.combustible_depths.client.particle.IgniteExplosionParticle;
import powercyphe.combustible_depths.client.particle.IgniteShardParticle;
import powercyphe.combustible_depths.client.render.PrimedIgniteEntityRenderer;
import powercyphe.combustible_depths.common.payload.IgniteExplosionPayload;
import powercyphe.combustible_depths.common.registry.CDBlocks;
import powercyphe.combustible_depths.common.registry.CDEntities;
import powercyphe.combustible_depths.common.registry.CDParticles;

public class CombustibleDepthsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.putBlocks(ChunkSectionLayer.CUTOUT, CDBlocks.IGNITE, CDBlocks.SOUL_IGNITE);

        EntityRenderers.register(CDEntities.PRIMED_IGNITE, PrimedIgniteEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(CDParticles.IGNITE_EXPLOSION, IgniteExplosionParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(CDParticles.SOUL_IGNITE_EXPLOSION, IgniteExplosionParticle.Provider::new);

        ParticleFactoryRegistry.getInstance().register(CDParticles.IGNITE_SHARD, IgniteShardParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(CDParticles.SOUL_IGNITE_SHARD, IgniteShardParticle.Provider::new);

        ClientPlayNetworking.registerGlobalReceiver(IgniteExplosionPayload.TYPE, new IgniteExplosionPayload.Receiver());
    }

    public static QuadEmitter modify(QuadEmitter emitter) {
        emitter = emitter.atlas(QuadAtlas.ITEM);
        return emitter;
    }
}
