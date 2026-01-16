package powercyphe.combustible_depths.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class IgniteExplosionParticle extends HugeExplosionParticle {
    public IgniteExplosionParticle(ClientLevel clientLevel, double d, double e, double f, double g, RandomSource random, SpriteSet spriteSet) {
        super(clientLevel, d, e, f, g, spriteSet);
        this.quadSize *= 1.5F;

        float col = 1F - random.nextFloat() * 0.3F;
        this.rCol = col;
        this.gCol = col;
        this.bCol = col;
    }

    public static class Provider extends HugeExplosionParticle.Provider {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            super(spriteSet);
            this.sprites = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
            return new IgniteExplosionParticle(clientLevel, d, e, f, g, RandomSource.create(), this.sprites);
        }
    }
}
