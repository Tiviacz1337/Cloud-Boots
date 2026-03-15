package com.tiviacz.cloudboots.forge;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.config.CloudBootsConfig;
import com.tiviacz.cloudboots.forge.compat.GoldenFeatherCurio;
import com.tiviacz.cloudboots.forge.init.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CloudBoots.MODID)
public final class CloudBootsForge {
    public static boolean curiosLoaded;

    public CloudBootsForge() {
        CloudBoots.init();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, CloudBootsConfig.serverSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CloudBootsConfig.clientSpec);

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(eventBus);
        eventBus.addListener(this::addCreative);
        eventBus.addListener(this::setup);

        curiosLoaded = ModList.get().isLoaded("curios");
    }

    public void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            if(curiosLoaded) GoldenFeatherCurio.registerCurio();
        });
    }

    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.CLOUD_BOOTS);
            event.accept(ModItems.IRON_CLOUD_BOOTS);
            event.accept(ModItems.GOLD_CLOUD_BOOTS);
            event.accept(ModItems.DIAMOND_CLOUD_BOOTS);
            event.accept(ModItems.NETHERITE_CLOUD_BOOTS);
        }

        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.GOLDEN_FEATHER);
        }
    }
}