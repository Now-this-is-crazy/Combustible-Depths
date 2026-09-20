package dev.powercyphe.combustible_depths.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import dev.powercyphe.combustible_depths.common.CombustibleDepths;

public interface CDTags {

    interface Items {
        TagKey<Item> IGNITE = key("ignite");
        TagKey<Item> IGNITES_IGNITE = key("ignites_ignite");

        TagKey<Item> IGNITE_CRAFTING_MATERIALS = key("ignite_crafting_materials");
        TagKey<Item> SOUL_IGNITE_CRAFTING_MATERIALS = key("soul_ignite_crafting_materials");

        static void init() {}

        static TagKey<Item> key(String id) {
            return CDTags.key(Registries.ITEM, id);
        }
    }

    interface Blocks {
        TagKey<Block> IGNITE = key("ignite");
        TagKey<Block> IGNITE_REPLACEABLE = key("ignite_replaceable");
        TagKey<Block> SOUL_IGNITE_REPLACEABLE = key("soul_ignite_replaceable");

        static void init() {}

        static TagKey<Block> key(String id) {
            return CDTags.key(Registries.BLOCK, id);
        }
    }

    static void init() {
        Items.init();
        Blocks.init();
    }

    static <T> TagKey<T> key(ResourceKey<Registry<T>> registry, String id) {
        return TagKey.create(registry, CombustibleDepths.id(id));
    }
}
