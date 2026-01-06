package powercyphe.combustible_depths.common.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import powercyphe.combustible_depths.common.CombustibleDepths;

public class CDSounds {

    public static final Holder<SoundEvent> IGNITE_IGNITE = register("ignite.ignite");
    public static final Holder<SoundEvent> IGNITE_CHARGE = register("ignite.charge");

    public static final Holder<SoundEvent> IGNITE_EXPLODE = register("ignite.explode");
    public static final Holder<SoundEvent> IGNITE_EXPLODE_FAR = register("ignite.explode_far");

    public static final Holder<SoundEvent> IGNITE_SHARD_LAND = register("ignite.shard_land");

    public static void init() {}

    public static Holder<SoundEvent> register(String name) {
        Identifier id = CombustibleDepths.id(name);
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }
}
