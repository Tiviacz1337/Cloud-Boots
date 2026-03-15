package com.tiviacz.cloudboots.neoforge;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.config.CloudBootsConfig;
import com.tiviacz.cloudboots.neoforge.compat.GoldenFeatherCurio;
import com.tiviacz.cloudboots.neoforge.init.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(CloudBoots.MODID)
public final class CloudBootsNeoForge {
    public static boolean curiosLoaded;

    public CloudBootsNeoForge(IEventBus eventBus, ModContainer modContainer) {
        CloudBoots.init();

        modContainer.registerConfig(ModConfig.Type.SERVER, CloudBootsConfig.serverSpec);
        modContainer.registerConfig(ModConfig.Type.CLIENT, CloudBootsConfig.clientSpec);
        if(FMLEnvironment.getDist() == Dist.CLIENT)
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

        ModItems.ITEMS.register(eventBus);
        eventBus.addListener(this::addCreative);

        curiosLoaded = ModList.get().isLoaded("curios");
        if(curiosLoaded) loadCuriosCompat(eventBus);
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

    private static void loadCuriosCompat(IEventBus bus) {
        bus.addListener(GoldenFeatherCurio::registerCurio);
    }
}
