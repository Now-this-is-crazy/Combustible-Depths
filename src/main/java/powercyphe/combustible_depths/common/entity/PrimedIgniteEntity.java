package powercyphe.combustible_depths.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ExplosionParticleInfo;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import powercyphe.combustible_depths.common.block.IgniteBlock;
import powercyphe.combustible_depths.common.payload.IgniteExplosionPayload;
import powercyphe.combustible_depths.common.registry.CDBlocks;
import powercyphe.combustible_depths.common.registry.CDEntities;
import powercyphe.combustible_depths.common.registry.CDSounds;

import java.util.List;

public class PrimedIgniteEntity extends Entity {
    public static final String FUSE_TIME_KEY = "fuseTime";
    public static final int FUSE_TIME_MAX = 30;
    private int fuseTime = 0;

    public static final String EXPLOSION_CHAIN_INDEX_KEY = "explosionChainIndex";
    public static final int EXPLOSION_CHAIN_INDEX_MAX = 50;
    private int explosionChainIndex = 0;

    public static final String BLOCKSTATE_KEY = "blockState";
    private BlockState blockState = CDBlocks.IGNITE.defaultBlockState();

    public Vec3 shakiness = this.updateShakiness();

    public PrimedIgniteEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public PrimedIgniteEntity(Level level, BlockPos blockPos, BlockState state, int explosionChainIndex) {
        super(CDEntities.PRIMED_IGNITE, level);
        this.setPos(blockPos.getBottomCenter());
        this.setNoGravity(true);
        this.setBlockState(state);
        this.explosionChainIndex = explosionChainIndex;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}

    @Override
    public void tick() {
        super.tick();
        Level level = this.level();

        if (this.fuseTime < FUSE_TIME_MAX) {
            if (this.fuseTime == 3 && this.explosionChainIndex == 0) {
                this.playSound(CDSounds.IGNITE_CHARGE.value(), 0.5F, 0.9F + this.getRandom().nextFloat() * 0.2F);
            }
            this.fuseTime++;
            this.shakiness = this.updateShakiness();
        } else {
            if (level instanceof ServerLevel) {
                Vec3 explosionPos = this.position().offsetRandom(this.getRandom(), 0.15F);
                ParticleOptions explosionParticle = IgniteBlock.getExplosionParticle(this.getBlockState());

                level.explode(
                        null, null, null,
                        explosionPos.x(), explosionPos.y(), explosionPos.z(),
                        2.35F, false, Level.ExplosionInteraction.BLOCK,
                        explosionParticle, explosionParticle,
                        WeightedList.<ExplosionParticleInfo>builder()
                                .add(new ExplosionParticleInfo(ParticleTypes.SMOKE, 1F, 0.7F))
                                .build(),
                        level.registryAccess().lookupOrThrow(Registries.SOUND_EVENT).wrapAsHolder(SoundEvents.EMPTY)
                );
                this.explode();
            }
            this.discard();
        }
    }

    public void explode() {
        AABB box = AABB.ofSize(this.position(), 80, 80, 80);
        List<ServerPlayer> players = this.level().getEntitiesOfClass(ServerPlayer.class, box, EntitySelector.ENTITY_STILL_ALIVE);

        for (ServerPlayer player : players) {
            IgniteExplosionPayload.send(player, this);
        }

    }
    public void setBlockState(BlockState blockState) {
        this.blockState = blockState;
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    public int getExplosionChainIndex() {
        return this.explosionChainIndex;
    }

    public void setFuseTime(int fuseTime) {
        this.fuseTime = fuseTime;
    }

    public int getFuseTime() {
        return this.fuseTime;
    }

    public Vec3 updateShakiness() {
        return new Vec3(
                this.getRandom().nextFloat() * 0.5F + 0.3F * (this.getRandom().nextBoolean() ? -1 : 1),
                this.getRandom().nextFloat() * 0.5F + 0.3F * (this.getRandom().nextBoolean() ? -1 : 1),
                this.getRandom().nextFloat() * 0.5F + 0.3F * (this.getRandom().nextBoolean() ? -1 : 1)
        );
    }

    @Override
    public boolean canBeCollidedWith(@Nullable Entity entity) {
        return true;
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float f) {
        return false;
    }

    @Override
    public boolean mayInteract(ServerLevel serverLevel, BlockPos blockPos) {
        return false;
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        this.setBlockState(valueInput.read(BLOCKSTATE_KEY, BlockState.CODEC).orElse(CDBlocks.IGNITE.defaultBlockState()));
        this.setFuseTime(valueInput.getIntOr(FUSE_TIME_KEY, 0));
        this.explosionChainIndex = valueInput.getIntOr(EXPLOSION_CHAIN_INDEX_KEY, 0);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        valueOutput.store(BLOCKSTATE_KEY, BlockState.CODEC, this.getBlockState());
        valueOutput.putInt(FUSE_TIME_KEY, this.getFuseTime());
        valueOutput.putInt(EXPLOSION_CHAIN_INDEX_KEY, this.explosionChainIndex);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity serverEntity) {
        return new ClientboundAddEntityPacket(this, serverEntity, Block.getId(this.getBlockState()));
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket clientboundAddEntityPacket) {
        super.recreateFromPacket(clientboundAddEntityPacket);
        this.setBlockState(Block.stateById(clientboundAddEntityPacket.getData()));
    }
}
