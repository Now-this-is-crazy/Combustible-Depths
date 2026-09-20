package dev.powercyphe.combustible_depths.common.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import dev.powercyphe.combustible_depths.common.CombustibleDepths;

public interface CDParticles {

    SimpleParticleType IGNITE_EXPLOSION = register("ignite_explosion", FabricParticleTypes.simple());
    SimpleParticleType SOUL_IGNITE_EXPLOSION = register("soul_ignite_explosion", FabricParticleTypes.simple());

    SimpleParticleType IGNITE_SHARD = register("ignite_shard", FabricParticleTypes.simple());
    SimpleParticleType SOUL_IGNITE_SHARD = register("soul_ignite_shard", FabricParticleTypes.simple());

    static void init() {}

    static <T extends ParticleType<? extends ParticleOptions>> T register(String id, T particleType) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, CombustibleDepths.id(id), particleType);
    }
}
