package com.tiviacz.cloudboots.fabric.datagen;

import com.tiviacz.cloudboots.fabric.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

    public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture, null);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.getResourceKey(ModItems.CLOUD_BOOTS))
                .add(ModItems.getResourceKey(ModItems.COPPER_CLOUD_BOOTS))
                .add(ModItems.getResourceKey(ModItems.IRON_CLOUD_BOOTS))
                .add(ModItems.getResourceKey(ModItems.GOLD_CLOUD_BOOTS))
                .add(ModItems.getResourceKey(ModItems.DIAMOND_CLOUD_BOOTS))
                .add(ModItems.getResourceKey(ModItems.NETHERITE_CLOUD_BOOTS));
        this.tag(ItemTags.FOOT_ARMOR)
                .add(ModItems.getResourceKey(ModItems.CLOUD_BOOTS))
                .add(ModItems.getResourceKey(ModItems.COPPER_CLOUD_BOOTS))
                .add(ModItems.getResourceKey(ModItems.IRON_CLOUD_BOOTS))
                .add(ModItems.getResourceKey(ModItems.GOLD_CLOUD_BOOTS))
                .add(ModItems.getResourceKey(ModItems.DIAMOND_CLOUD_BOOTS))
                .add(ModItems.getResourceKey(ModItems.NETHERITE_CLOUD_BOOTS));
    }
}
