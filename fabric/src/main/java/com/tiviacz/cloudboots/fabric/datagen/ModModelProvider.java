package com.tiviacz.cloudboots.fabric.datagen;

import com.tiviacz.cloudboots.fabric.init.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.world.item.equipment.trim.TrimMaterials;

import java.util.Map;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateTrimmableItem(ModItems.CLOUD_BOOTS, ItemModelGenerators.TRIM_PREFIX_BOOTS, false, Map.of(TrimMaterials.Palette.DIAMOND, TrimMaterials.Palette.DIAMOND_DARKER));
        itemModelGenerator.generateTrimmableItem(ModItems.COPPER_CLOUD_BOOTS, ItemModelGenerators.TRIM_PREFIX_BOOTS, false, Map.of(TrimMaterials.Palette.COPPER, TrimMaterials.Palette.COPPER_DARKER));
        itemModelGenerator.generateTrimmableItem(ModItems.IRON_CLOUD_BOOTS, ItemModelGenerators.TRIM_PREFIX_BOOTS, false, Map.of(TrimMaterials.Palette.IRON, TrimMaterials.Palette.IRON_DARKER));
        itemModelGenerator.generateTrimmableItem(ModItems.GOLD_CLOUD_BOOTS, ItemModelGenerators.TRIM_PREFIX_BOOTS, false, Map.of(TrimMaterials.Palette.GOLD, TrimMaterials.Palette.GOLD_DARKER));
        itemModelGenerator.generateTrimmableItem(ModItems.DIAMOND_CLOUD_BOOTS, ItemModelGenerators.TRIM_PREFIX_BOOTS, false, Map.of(TrimMaterials.Palette.DIAMOND, TrimMaterials.Palette.DIAMOND_DARKER));
        itemModelGenerator.generateTrimmableItem(ModItems.NETHERITE_CLOUD_BOOTS, ItemModelGenerators.TRIM_PREFIX_BOOTS, false, Map.of(TrimMaterials.Palette.NETHERITE, TrimMaterials.Palette.NETHERITE_DARKER));
        itemModelGenerator.generateFlatItem(ModItems.GOLDEN_FEATHER, ModelTemplates.FLAT_ITEM);
    }
}