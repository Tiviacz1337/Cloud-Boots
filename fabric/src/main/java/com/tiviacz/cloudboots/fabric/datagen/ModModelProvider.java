package com.tiviacz.cloudboots.fabric.datagen;

import com.tiviacz.cloudboots.fabric.init.ModItems;
import com.tiviacz.cloudboots.init.ModArmorMaterials;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateTrimmableItem(ModItems.CLOUD_BOOTS, ModArmorMaterials.CLOUD_ID, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModelGenerator.generateTrimmableItem(ModItems.COPPER_CLOUD_BOOTS, ModArmorMaterials.COPPER_CLOUD_ID, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModelGenerator.generateTrimmableItem(ModItems.IRON_CLOUD_BOOTS, ModArmorMaterials.IRON_CLOUD_ID, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModelGenerator.generateTrimmableItem(ModItems.GOLD_CLOUD_BOOTS, ModArmorMaterials.GOLD_CLOUD_ID, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModelGenerator.generateTrimmableItem(ModItems.DIAMOND_CLOUD_BOOTS, ModArmorMaterials.DIAMOND_CLOUD_ID, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModelGenerator.generateTrimmableItem(ModItems.NETHERITE_CLOUD_BOOTS, ModArmorMaterials.NETHERITE_CLOUD_ID, ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
        itemModelGenerator.generateFlatItem(ModItems.GOLDEN_FEATHER, ModelTemplates.FLAT_ITEM);
    }
}