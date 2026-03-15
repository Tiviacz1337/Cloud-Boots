package com.tiviacz.cloudboots.fabric.datagen;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.fabric.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> writer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLDEN_FEATHER, 1)
                .define('A', Items.FEATHER).define('B', Items.GOLD_INGOT)
                .pattern("AAA").pattern("ABA").pattern("AAA")
                .unlockedBy("has_feathers", has(Items.FEATHER)).save(writer, id("golden_feather"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.IRON_CLOUD_BOOTS, 1)
                .define('A', ModItems.GOLDEN_FEATHER).define('B', Items.IRON_INGOT)
                .pattern("A A").pattern("B B").pattern("B B")
                .unlockedBy("has_iron_ingots", has(Items.IRON_INGOT)).save(writer, id("iron_cloud_boots"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.GOLD_CLOUD_BOOTS, 1)
                .define('A', ModItems.GOLDEN_FEATHER).define('B', Items.GOLD_INGOT)
                .pattern("A A").pattern("B B").pattern("B B")
                .unlockedBy("has_gold_ingots", has(Items.GOLD_INGOT)).save(writer, id("gold_cloud_boots"));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DIAMOND_CLOUD_BOOTS, 1)
                .define('A', ModItems.GOLDEN_FEATHER).define('B', Items.DIAMOND)
                .pattern("A A").pattern("B B").pattern("B B")
                .unlockedBy("has_diamond_gems", has(Items.DIAMOND)).save(writer, id("diamond_cloud_boots"));

        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.of(ModItems.DIAMOND_CLOUD_BOOTS),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        RecipeCategory.TOOLS, ModItems.NETHERITE_CLOUD_BOOTS)
                .unlocks("has_netherite_ingots", has(Items.NETHERITE_INGOT)).save(writer, id("netherite_cloud_boots"));
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(CloudBoots.MODID, name);
    }

    @Override
    public String getName() {
        return "Cloud Boots Recipes";
    }
}