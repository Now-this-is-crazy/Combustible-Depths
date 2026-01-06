package powercyphe.combustible_depths.common.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import powercyphe.combustible_depths.common.CombustibleDepths;

public class CDParticles {

    public static ParticleType<SimpleParticleType> IGNITE_EXPLOSION = register("ignite_explosion", FabricParticleTypes.simple());
    public static ParticleType<SimpleParticleType> SOUL_IGNITE_EXPLOSION = register("soul_ignite_explosion", FabricParticleTypes.simple());

    public static ParticleType<SimpleParticleType> IGNITE_SHARD = register("ignite_shard", FabricParticleTypes.simple());
    public static ParticleType<SimpleParticleType> SOUL_IGNITE_SHARD = register("soul_ignite_shard", FabricParticleTypes.simple());

    public static void init() {}

    public static <T extends ParticleOptions> ParticleType<T> register(String id, ParticleType<T> particleType) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, CombustibleDepths.id(id), particleType);
    }
}
