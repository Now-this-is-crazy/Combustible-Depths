package dev.powercyphe.combustible_depths.common.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import dev.powercyphe.combustible_depths.common.CombustibleDepths;

public interface CDSounds {

    Holder<SoundEvent> IGNITE_IGNITE = register("ignite.ignite");
    Holder<SoundEvent> IGNITE_CHARGE = register("ignite.charge");

    Holder<SoundEvent> IGNITE_EXPLODE = register("ignite.explode");
    Holder<SoundEvent> IGNITE_EXPLODE_FAR = register("ignite.explode_far");

    Holder<SoundEvent> IGNITE_SHARD_LAND = register("ignite.shard_land");

    static void init() {}

    static Holder<SoundEvent> register(String name) {
        Identifier id = CombustibleDepths.id(name);
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }
}
