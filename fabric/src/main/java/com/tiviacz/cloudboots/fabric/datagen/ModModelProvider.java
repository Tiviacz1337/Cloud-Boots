package com.tiviacz.cloudboots.fabric.datagen;

import com.tiviacz.cloudboots.fabric.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.world.item.ArmorItem;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateArmorTrims((ArmorItem)ModItems.CLOUD_BOOTS);
        itemModelGenerator.generateArmorTrims((ArmorItem)ModItems.IRON_CLOUD_BOOTS);
        itemModelGenerator.generateArmorTrims((ArmorItem)ModItems.GOLD_CLOUD_BOOTS);
        itemModelGenerator.generateArmorTrims((ArmorItem)ModItems.DIAMOND_CLOUD_BOOTS);
        itemModelGenerator.generateArmorTrims((ArmorItem)ModItems.NETHERITE_CLOUD_BOOTS);
        itemModelGenerator.generateFlatItem(ModItems.GOLDEN_FEATHER, ModelTemplates.FLAT_ITEM);
    }
}