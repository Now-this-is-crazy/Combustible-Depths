package powercyphe.combustible_depths.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import powercyphe.combustible_depths.common.CombustibleDepths;
import powercyphe.combustible_depths.common.entity.PrimedIgniteEntity;

public class CDEntities {

    public static final EntityType<PrimedIgniteEntity> PRIMED_IGNITE = register("primed_ignite",
            EntityType.Builder.<PrimedIgniteEntity>of(PrimedIgniteEntity::new, MobCategory.MISC).sized(1F, 1F)
    );

    public static void init() {}

    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> entityType) {
        ResourceKey<EntityType<?>> entityKey = ResourceKey.create(Registries.ENTITY_TYPE, CombustibleDepths.id(id));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, entityKey, entityType.build(entityKey));
    }
}
