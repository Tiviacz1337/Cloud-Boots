package com.tiviacz.cloudboots.fabric;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.config.CloudBootsConfig;
import com.tiviacz.cloudboots.fabric.compat.GoldenFeatherTrinket;
import com.tiviacz.cloudboots.fabric.init.ModItems;
import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.fml.config.ModConfig;

public final class CloudBootsFabric implements ModInitializer {
    public static boolean trinketsLoaded;

    @Override
    public void onInitialize() {
        CloudBoots.init();
        ConfigRegistry.INSTANCE.register(CloudBoots.MODID, ModConfig.Type.SERVER, CloudBootsConfig.serverSpec);
        ModItems.register();
        addCreative();

        trinketsLoaded = FabricLoader.getInstance().isModLoaded("trinkets");
        if(trinketsLoaded) GoldenFeatherTrinket.registerTrinket();
    }

    public void addCreative() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(tab -> {
            tab.accept(ModItems.CLOUD_BOOTS);
            tab.accept(ModItems.COPPER_CLOUD_BOOTS);
            tab.accept(ModItems.IRON_CLOUD_BOOTS);
            tab.accept(ModItems.GOLD_CLOUD_BOOTS);
            tab.accept(ModItems.DIAMOND_CLOUD_BOOTS);
            tab.accept(ModItems.NETHERITE_CLOUD_BOOTS);
        });
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(tab -> {
            tab.accept(ModItems.GOLDEN_FEATHER);
        });
    }
}