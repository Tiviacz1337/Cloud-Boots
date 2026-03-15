package com.tiviacz.cloudboots.platform.fabric;

import com.tiviacz.cloudboots.fabric.CloudBootsFabric;
import com.tiviacz.cloudboots.init.ModItems;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class PlatformImpl {
    public static Supplier<Item> getItem(ResourceLocation id) {
        if(id.equals(ModItems.CLOUD_BOOTS_ID)) {
            return () -> com.tiviacz.cloudboots.fabric.init.ModItems.CLOUD_BOOTS;
        }
        if(id.equals(ModItems.IRON_CLOUD_BOOTS_ID)) {
            return () -> com.tiviacz.cloudboots.fabric.init.ModItems.IRON_CLOUD_BOOTS;
        }
        if(id.equals(ModItems.GOLD_CLOUD_BOOTS_ID)) {
            return () -> com.tiviacz.cloudboots.fabric.init.ModItems.GOLD_CLOUD_BOOTS;
        }
        if(id.equals(ModItems.DIAMOND_CLOUD_BOOTS_ID)) {
            return () -> com.tiviacz.cloudboots.fabric.init.ModItems.DIAMOND_CLOUD_BOOTS;
        }
        if(id.equals(ModItems.NETHERITE_CLOUD_BOOTS_ID)) {
            return () -> com.tiviacz.cloudboots.fabric.init.ModItems.NETHERITE_CLOUD_BOOTS;
        }
        if(id.equals(ModItems.GOLDEN_FEATHER_ID)) {
            return () -> com.tiviacz.cloudboots.fabric.init.ModItems.GOLDEN_FEATHER;
        }
        throw new IllegalArgumentException("Unknown item: " + id);
    }

    public static boolean isGoldenFeatherEquipped(LivingEntity livingEntity) {
        AtomicBoolean isEquipped = new AtomicBoolean(false);
        if(CloudBootsFabric.trinketsLoaded) {
            TrinketsApi.getTrinketComponent(livingEntity).ifPresent(trinkets -> isEquipped.set(trinkets.isEquipped(com.tiviacz.cloudboots.fabric.init.ModItems.GOLDEN_FEATHER)));
        }
        return isEquipped.get();
    }
}