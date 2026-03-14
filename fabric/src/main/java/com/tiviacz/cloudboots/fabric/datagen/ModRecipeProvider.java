package com.tiviacz.cloudboots.fabric.datagen;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.fabric.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> holderProvider) {
        super(output, holderProvider);
    }

    @Override
    public void buildRecipes(RecipeOutput writer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLDEN_FEATHER, 1)
                .define('A', ConventionalItemTags.FEATHERS).define('B', ConventionalItemTags.GOLD_INGOTS)
                .pattern("AAA").pattern("ABA").pattern("AAA")
                .unlockedBy("has_feathers", has(ConventionalItemTags.FEATHERS)).save(writer, id("golden_feather"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.IRON_CLOUD_BOOTS, 1)
                .define('A', ModItems.GOLDEN_FEATHER).define('B', ConventionalItemTags.IRON_INGOTS)
                .pattern("A A").pattern("B B").pattern("B B")
                .unlockedBy("has_iron_ingots", has(ConventionalItemTags.IRON_INGOTS)).save(writer, id("iron_cloud_boots"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.GOLD_CLOUD_BOOTS, 1)
                .define('A', ModItems.GOLDEN_FEATHER).define('B', ConventionalItemTags.GOLD_INGOTS)
                .pattern("A A").pattern("B B").pattern("B B")
                .unlockedBy("has_gold_ingots", has(ConventionalItemTags.GOLD_INGOTS)).save(writer, id("gold_cloud_boots"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DIAMOND_CLOUD_BOOTS, 1)
                .define('A', ModItems.GOLDEN_FEATHER).define('B', ConventionalItemTags.DIAMOND_GEMS)
                .pattern("A A").pattern("B B").pattern("B B")
                .unlockedBy("has_diamond_gems", has(ConventionalItemTags.DIAMOND_GEMS)).save(writer, id("diamond_cloud_boots"));

        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.of(ModItems.DIAMOND_CLOUD_BOOTS),
                        Ingredient.of(ConventionalItemTags.NETHERITE_INGOTS),
                        RecipeCategory.TOOLS, ModItems.NETHERITE_CLOUD_BOOTS)
                .unlocks("has_netherite_ingots", has(ConventionalItemTags.NETHERITE_INGOTS)).save(writer, id("netherite_cloud_boots"));
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, name);
    }

    @Override
    public String getName() {
        return "Cloud Boots Recipes";
    }
}