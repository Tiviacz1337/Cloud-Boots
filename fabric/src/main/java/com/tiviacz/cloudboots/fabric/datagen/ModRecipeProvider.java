package com.tiviacz.cloudboots.fabric.datagen;

import com.tiviacz.cloudboots.fabric.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> holderProvider) {
        super(output, holderProvider);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new RecipeProvider(recipes, advancements) {
            @Override
            public void buildRecipes() {
                ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.GOLDEN_FEATHER, 1)
                        .define('A', ConventionalItemTags.GOLD_INGOTS).define('B', ConventionalItemTags.FEATHERS)
                        .pattern("AAA").pattern("ABA").pattern("AAA")
                        .unlockedBy("has_feathers", has(ConventionalItemTags.FEATHERS)).save(output);
                ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.TOOLS, ModItems.COPPER_CLOUD_BOOTS, 1)
                        .define('A', ModItems.GOLDEN_FEATHER).define('B', ConventionalItemTags.COPPER_INGOTS)
                        .pattern("A A").pattern("B B").pattern("B B")
                        .unlockedBy("has_copper_ingots", has(ConventionalItemTags.COPPER_INGOTS)).save(output);
                ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.TOOLS, ModItems.IRON_CLOUD_BOOTS, 1)
                        .define('A', ModItems.GOLDEN_FEATHER).define('B', ConventionalItemTags.IRON_INGOTS)
                        .pattern("A A").pattern("B B").pattern("B B")
                        .unlockedBy("has_iron_ingots", has(ConventionalItemTags.IRON_INGOTS)).save(output);
                ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.TOOLS, ModItems.GOLD_CLOUD_BOOTS, 1)
                        .define('A', ModItems.GOLDEN_FEATHER).define('B', ConventionalItemTags.GOLD_INGOTS)
                        .pattern("A A").pattern("B B").pattern("B B")
                        .unlockedBy("has_gold_ingots", has(ConventionalItemTags.GOLD_INGOTS)).save(output);
                ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.TOOLS, ModItems.DIAMOND_CLOUD_BOOTS, 1)
                        .define('A', ModItems.GOLDEN_FEATHER).define('B', ConventionalItemTags.DIAMOND_GEMS)
                        .pattern("A A").pattern("B B").pattern("B B")
                        .unlockedBy("has_diamond_gems", has(ConventionalItemTags.DIAMOND_GEMS)).save(output);

                SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                                Ingredient.of(ModItems.DIAMOND_CLOUD_BOOTS),
                                Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(ConventionalItemTags.NETHERITE_INGOTS)),
                                RecipeCategory.TOOLS, ModItems.NETHERITE_CLOUD_BOOTS)
                        .unlocks("has_netherite_ingots", has(ConventionalItemTags.NETHERITE_INGOTS)).save(output, getItemName(ModItems.NETHERITE_CLOUD_BOOTS) + "_smithing");
            }
        };
    }

    @Override
    public String getName() {
        return "Cloud Boots Recipes";
    }
}