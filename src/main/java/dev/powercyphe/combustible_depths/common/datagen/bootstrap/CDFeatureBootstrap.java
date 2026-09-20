package dev.powercyphe.combustible_depths.common.datagen.bootstrap;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.ScatteredOreFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import dev.powercyphe.combustible_depths.common.registry.CDBlocks;
import dev.powercyphe.combustible_depths.common.registry.CDFeatures;
import dev.powercyphe.combustible_depths.common.registry.CDPlacedFeatures;
import dev.powercyphe.combustible_depths.common.registry.CDTags;

import java.util.Set;


public class CDFeatureBootstrap implements MultiRegistryBootstrap {
    @Override
    public Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
        return Set.of(Registries.FEATURE, Registries.PLACED_FEATURE);
    }

    @Override
    public void run(BootstrapGetter getter) {
        BootstrapContext<Feature> ctx = getter.get(Registries.FEATURE);

        RuleTest iReplaceable = RuleTest.anyOf(new TagMatchTest(CDTags.Blocks.IGNITE_REPLACEABLE));
        RuleTest sIReplaceable = RuleTest.anyOf(new TagMatchTest(CDTags.Blocks.SOUL_IGNITE_REPLACEABLE));

        Holder<Feature> ignite = ctx.register(CDFeatures.ORE_IGNITE, new OreFeature(iReplaceable, CDBlocks.IGNITE.defaultBlockState(), 9));
        Holder<Feature> igniteScattered = ctx.register(CDFeatures.ORE_IGNITE_SCATTERED, new ScatteredOreFeature(iReplaceable, CDBlocks.IGNITE.defaultBlockState(), 9, 0F));

        Holder<Feature> soulIgnite = ctx.register(CDFeatures.ORE_SOUL_IGNITE, new OreFeature(sIReplaceable, CDBlocks.SOUL_IGNITE.defaultBlockState(), 9));
        Holder<Feature> soulIgniteScattered = ctx.register(CDFeatures.ORE_SOUL_IGNITE_SCATTERED, new ScatteredOreFeature(sIReplaceable, CDBlocks.SOUL_IGNITE.defaultBlockState(), 9, 0F));

        PlacementModifier[] lowerMod = new PlacementModifier[]{
                CountPlacement.of(11),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(5), VerticalAnchor.absolute(30)),
                BiomeFilter.biome()
        };
        PlacementModifier[] upperMod = new PlacementModifier[]{
                CountPlacement.of(33),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(30), VerticalAnchor.absolute(256)),
                BiomeFilter.biome()
        };

        register(getter, CDPlacedFeatures.ORE_IGNITE_LOWER, ignite, lowerMod);
        register(getter, CDPlacedFeatures.ORE_IGNITE_UPPER, igniteScattered, upperMod);

        register(getter, CDPlacedFeatures.ORE_SOUL_IGNITE_LOWER, soulIgnite, lowerMod);
        register(getter, CDPlacedFeatures.ORE_SOUL_IGNITE_UPPER, soulIgniteScattered, upperMod);
    }

    public void register(BootstrapGetter getter, ResourceKey<PlacedFeature> placedFeatureKey, Holder<Feature> feature, PlacementModifier... modifiers) {
        BootstrapContext<PlacedFeature> ctx = getter.get(Registries.PLACED_FEATURE);
        PlacementUtils.register(ctx, placedFeatureKey, feature, modifiers);
    }
}
