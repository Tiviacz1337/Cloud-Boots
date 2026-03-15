package com.tiviacz.cloudboots.fabric;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.config.CloudBootsConfig;
import com.tiviacz.cloudboots.fabric.compat.GoldenFeatherTrinket;
import com.tiviacz.cloudboots.fabric.init.ModItems;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.fml.config.ModConfig;

public final class CloudBootsFabric implements ModInitializer {
    public static boolean trinketsLoaded;

    @Override
    public void onInitialize() {
        CloudBoots.init();
        ForgeConfigRegistry.INSTANCE.register(CloudBoots.MODID, ModConfig.Type.SERVER, CloudBootsConfig.serverSpec);
        ForgeConfigRegistry.INSTANCE.register(CloudBoots.MODID, ModConfig.Type.CLIENT, CloudBootsConfig.clientSpec);
        ModItems.register();
        addCreative();

        trinketsLoaded = FabricLoader.getInstance().isModLoaded("trinkets");
        if(trinketsLoaded) GoldenFeatherTrinket.registerTrinket();
    }

    public void addCreative() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(tab -> {
            tab.accept(ModItems.CLOUD_BOOTS);
            tab.accept(ModItems.IRON_CLOUD_BOOTS);
            tab.accept(ModItems.GOLD_CLOUD_BOOTS);
            tab.accept(ModItems.DIAMOND_CLOUD_BOOTS);
            tab.accept(ModItems.NETHERITE_CLOUD_BOOTS);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(tab -> {
            tab.accept(ModItems.GOLDEN_FEATHER);
        });
    }
}
