package powercyphe.combustible_depths.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import powercyphe.combustible_depths.common.registry.CDSounds;

public class IgniteShardParticle extends TextureSheetParticle {
    private boolean playedHitSound = false;

    public IgniteShardParticle(ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random, TextureAtlasSprite sprite) {
        super(clientLevel, x, y, z);

        this.xd = velocityX;
        this.yd = velocityY;
        this.zd = velocityZ;
        this.gravity = 0.54F + random.nextFloat() * 0.46F;

        this.roll = this.random.nextFloat();
        this.oRoll = this.roll;

        this.lifetime = 53 + random.nextInt(47);
        this.quadSize = 0.13F + random.nextFloat() * 0.11F;

        this.tick();
        this.setSprite(sprite);
    }

    @Override
    public void tick() {
        super.tick();

        this.oRoll = this.roll;
        this.roll += this.random.nextFloat()
                * ((float) (Math.abs(this.xd) + Math.abs(this.yd) + Math.abs(this.zd)) / 3F)
                * (this.onGround ? 0F : 1F);

        if (this.age++ > this.lifetime) {
            this.remove();
        } else {
            if (this.onGround && !this.playedHitSound) {
                this.playedHitSound = true;
                Minecraft.getInstance().getSoundManager().play(
                        new SimpleSoundInstance(
                                CDSounds.IGNITE_SHARD_LAND.value(), SoundSource.BLOCKS,
                                0.05F, 0.8F + RandomSource.create().nextFloat() * 0.4F, this.random, this.x, this.y, this.z)
                );
            } else if (!this.onGround) {
                this.playedHitSound = false;

                BlockPos blockPos = this.getBlockPos();
                FluidState fluid = this.level.getFluidState(blockPos);
                if (fluid.is(FluidTags.LAVA)) {
                    AABB box = fluid.getShape(this.level, blockPos).bounds();

                    if (box != null && box.intersects(this.getBoundingBox())) {
                        Vec3 lavaPos = this.getTopOfLava();

                        this.level.playSound(null, lavaPos.x(), lavaPos.y(), lavaPos.z(), SoundEvents.LAVA_POP, SoundSource.BLOCKS, 0.5F, 0.8F + this.random.nextFloat() * 0.4F);
                        this.level.addParticle(ParticleTypes.LAVA, lavaPos.x(), lavaPos.y(), lavaPos.z(), 0, 1, 0);
                        this.remove();
                        return;
                    }
                }
            }
            int remainingTime = this.lifetime - this.age;
            if (remainingTime <= 10) {
                this.alpha = Math.max(0, remainingTime / 10F);
            }
        }
    }

    public BlockPos getBlockPos() {
        return new BlockPos((int) this.x, (int) this.y, (int) this.z);
    }

    public Vec3 getTopOfLava() {
        BlockPos blockPos = this.getBlockPos();

        FluidState fluid = null;
        while (true) {
            FluidState check = this.level.getFluidState(blockPos);
            if (check.is(FluidTags.LAVA)) {
                fluid = check;
            } else {
                break;
            }
            blockPos = blockPos.above();
        }

        if (fluid != null) {
            blockPos = blockPos.below();
            AABB box = fluid.getShape(this.level, blockPos).bounds();

            if (box != null) {
                return new Vec3(this.x, blockPos.getY() + (box.maxY - box.minY), this.z);
            } else {
                return new Vec3(this.x, blockPos.getY(), this.z);
            }
        }
        return new Vec3(this.x, this.y, this.z);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
        @Override
        public @Nullable Particle createParticle(SimpleParticleType particleOptions, ClientLevel clientLevel, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            RandomSource randomSource = RandomSource.create();
            return new IgniteShardParticle(clientLevel, x, y, z, velocityX, velocityY, velocityZ, randomSource, this.sprites.get(randomSource));
        }
    }
}
