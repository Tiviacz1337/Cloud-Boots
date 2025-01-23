package com.tiviacz.cloudboots.neoforge;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.neoforge.compat.GoldenFeatherCurio;
import com.tiviacz.cloudboots.init.neoforge.ModItemsImpl;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(CloudBoots.MODID)
public final class CloudBootsNeoForge {
    public static boolean curiosLoaded;

    public CloudBootsNeoForge(IEventBus eventBus, ModContainer modContainer) {
        // Run our common setup.
        CloudBoots.init();

        ModItemsImpl.ITEMS.register(eventBus);
        eventBus.addListener(this::addCreative);

        curiosLoaded = ModList.get().isLoaded("curios");
        if(curiosLoaded) loadCuriosCompat(eventBus);
    }

    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItemsImpl.CLOUD_BOOTS);
            event.accept(ModItemsImpl.IRON_CLOUD_BOOTS);
            event.accept(ModItemsImpl.GOLD_CLOUD_BOOTS);
            event.accept(ModItemsImpl.DIAMOND_CLOUD_BOOTS);
            event.accept(ModItemsImpl.NETHERITE_CLOUD_BOOTS);
        }

        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItemsImpl.GOLDEN_FEATHER);
        }
    }

    private static void loadCuriosCompat(IEventBus bus) {
        bus.addListener(GoldenFeatherCurio::registerCurio);
    }
}
