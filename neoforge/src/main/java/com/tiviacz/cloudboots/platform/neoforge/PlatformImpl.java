package com.tiviacz.cloudboots.platform.neoforge;

import com.tiviacz.cloudboots.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class PlatformImpl {
    public static Supplier<Item> getItem(ResourceLocation id) {
        if(id.equals(ModItems.CLOUD_BOOTS_ID)) {
            return com.tiviacz.cloudboots.neoforge.init.ModItems.CLOUD_BOOTS;
        }
        if(id.equals(ModItems.IRON_CLOUD_BOOTS_ID)) {
            return com.tiviacz.cloudboots.neoforge.init.ModItems.IRON_CLOUD_BOOTS;
        }
        if(id.equals(ModItems.GOLD_CLOUD_BOOTS_ID)) {
            return com.tiviacz.cloudboots.neoforge.init.ModItems.GOLD_CLOUD_BOOTS;
        }
        if(id.equals(ModItems.DIAMOND_CLOUD_BOOTS_ID)) {
            return com.tiviacz.cloudboots.neoforge.init.ModItems.DIAMOND_CLOUD_BOOTS;
        }
        if(id.equals(ModItems.NETHERITE_CLOUD_BOOTS_ID)) {
            return com.tiviacz.cloudboots.neoforge.init.ModItems.NETHERITE_CLOUD_BOOTS;
        }
        if(id.equals(ModItems.GOLDEN_FEATHER_ID)) {
            return com.tiviacz.cloudboots.neoforge.init.ModItems.GOLDEN_FEATHER;
        }
        throw new IllegalArgumentException("Unknown item: " + id);
    }
}