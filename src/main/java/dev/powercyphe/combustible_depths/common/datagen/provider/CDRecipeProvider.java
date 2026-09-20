package dev.powercyphe.combustible_depths.common.datagen.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import dev.powercyphe.combustible_depths.common.registry.CDBlocks;
import dev.powercyphe.combustible_depths.common.registry.CDTags;

import java.util.concurrent.CompletableFuture;

public class CDRecipeProvider extends FabricRecipeProvider {
    public CDRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new RecipeProvider(recipes, advancements) {
            @Override
            public void buildRecipes() {
                shaped(RecipeCategory.MISC, CDBlocks.IGNITE, 2)
                        .pattern("FGF")
                        .pattern("GCG")
                        .pattern("FGF")
                        .define('F', CDTags.Items.IGNITE_CRAFTING_MATERIALS)
                        .define('G', Items.GUNPOWDER)
                        .define('C', Items.FIRE_CHARGE)
                        .unlockedBy("has_gunpowder", has(Items.GUNPOWDER))
                        .save(output);

                shaped(RecipeCategory.MISC, CDBlocks.SOUL_IGNITE, 2)
                        .pattern("SGS")
                        .pattern("GCG")
                        .pattern("SGS")
                        .define('S', CDTags.Items.SOUL_IGNITE_CRAFTING_MATERIALS)
                        .define('G', Items.GUNPOWDER)
                        .define('C', Items.FIRE_CHARGE)
                        .unlockedBy("has_gunpowder", has(Items.GUNPOWDER))
                        .save(output);

                shapeless(RecipeCategory.MISC, Items.GUNPOWDER, 2)
                        .requires(CDTags.Items.IGNITE)
                        .unlockedBy("has_ignite", has(CDTags.Items.IGNITE))
                        .save(output);
            }
        };
    }
}
