package com.tiviacz.cloudboots.fabric;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.fabric.compat.GoldenFeatherTrinket;
import com.tiviacz.cloudboots.init.fabric.ModItemsImpl;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.CreativeModeTabs;

public final class CloudBootsFabric implements ModInitializer {
    public static boolean trinketsLoaded;

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        CloudBoots.init();
        ModItemsImpl.register();
        addCreative();

        trinketsLoaded = FabricLoader.getInstance().isModLoaded("trinkets");
        if(trinketsLoaded) GoldenFeatherTrinket.registerTrinket();
    }

    public void addCreative() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(tab -> {
            tab.accept(ModItemsImpl.CLOUD_BOOTS);
            tab.accept(ModItemsImpl.IRON_CLOUD_BOOTS);
            tab.accept(ModItemsImpl.GOLD_CLOUD_BOOTS);
            tab.accept(ModItemsImpl.DIAMOND_CLOUD_BOOTS);
            tab.accept(ModItemsImpl.NETHERITE_CLOUD_BOOTS);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(tab -> {
            tab.accept(ModItemsImpl.GOLDEN_FEATHER);
        });
    }
}
