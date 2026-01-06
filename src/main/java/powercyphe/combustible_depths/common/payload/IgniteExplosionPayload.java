package powercyphe.combustible_depths.common.payload;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import powercyphe.combustible_depths.common.CombustibleDepths;
import powercyphe.combustible_depths.common.block.IgniteBlock;
import powercyphe.combustible_depths.common.entity.PrimedIgniteEntity;
import powercyphe.combustible_depths.common.registry.CDSounds;

public record IgniteExplosionPayload(BlockState state, Vec3 pos, int explosionChainIndex) implements CustomPacketPayload {
    public static final Type<IgniteExplosionPayload> TYPE = new Type<>(CombustibleDepths.id("ignite_explosion"));
    public static final StreamCodec<ByteBuf, IgniteExplosionPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY), IgniteExplosionPayload::state,
            Vec3.STREAM_CODEC, IgniteExplosionPayload::pos,
            ByteBufCodecs.INT, IgniteExplosionPayload::explosionChainIndex,
            IgniteExplosionPayload::new
    );

    public static void send(ServerPlayer serverPlayer, PrimedIgniteEntity entity) {
        ServerPlayNetworking.send(serverPlayer, new IgniteExplosionPayload(entity.getBlockState(), entity.position(), entity.getExplosionChainIndex()));
    }


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<IgniteExplosionPayload> {
        @Override
        public void receive(IgniteExplosionPayload payload, ClientPlayNetworking.Context context) {
            Level level = context.client().level;

            if (level != null) {
                BlockState state = payload.state();
                Vec3 pos = payload.pos();

                float explosionChainIndex = (float) payload.explosionChainIndex();
                boolean isFar = this.isFarAwayFromCamera(payload.pos());

                float pitch = 1F + (explosionChainIndex / (float) PrimedIgniteEntity.EXPLOSION_CHAIN_INDEX_MAX);
                level.playLocalSound(
                        pos.x(), pos.y(), pos.z(),
                        (isFar ? CDSounds.IGNITE_EXPLODE_FAR : CDSounds.IGNITE_EXPLODE).value(), SoundSource.BLOCKS,
                        isFar ? 10F : 0.5F, pitch, true
                );

                RandomSource random = RandomSource.create();
                ParticleOptions shardParticle = IgniteBlock.getShardParticle(state);

                for (int i = 0; i < 30; i++) {
                    level.addParticle(shardParticle,
                            pos.x(), pos.y(), pos.z(),
                            (random.nextFloat() - 0.5F),
                            (random.nextFloat() - 0.15F),
                            (random.nextFloat() - 0.5F)
                    );
                }
            }
        }

        private boolean isFarAwayFromCamera(Vec3 pos) {
            Minecraft minecraft = Minecraft.getInstance();
            return minecraft.gameRenderer.getMainCamera().position().distanceToSqr(pos.x(), pos.y(), pos.z()) >= 256;
        }
    }
}
