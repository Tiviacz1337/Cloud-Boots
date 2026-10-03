package com.tiviacz.cloudboots.fabric.datagen;

import com.tiviacz.cloudboots.fabric.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        this.getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.CLOUD_BOOTS)
                .add(ModItems.IRON_CLOUD_BOOTS)
                .add(ModItems.GOLD_CLOUD_BOOTS)
                .add(ModItems.DIAMOND_CLOUD_BOOTS)
                .add(ModItems.NETHERITE_CLOUD_BOOTS);
    }
}