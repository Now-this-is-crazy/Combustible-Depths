package powercyphe.combustible_depths.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import powercyphe.combustible_depths.common.CombustibleDepths;

public class CDTags {

    public static final TagKey<Item> IGNITES_IGNITE = key(Registries.ITEM, "ignites_ignite");

    public static <T> TagKey<T> key(ResourceKey<Registry<T>> registry, String id) {
        return TagKey.create(registry, CombustibleDepths.id(id));
    }
}
