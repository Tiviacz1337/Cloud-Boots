package com.tiviacz.cloudboots.platform.forge;

import com.tiviacz.cloudboots.init.ModItems;
import com.tiviacz.cloudboots.forge.CloudBootsForge;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosCapability;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class PlatformImpl {
    public static Supplier<Item> getItem(ResourceLocation id) {
        if(id.equals(ModItems.CLOUD_BOOTS_ID)) {
            return com.tiviacz.cloudboots.forge.init.ModItems.CLOUD_BOOTS;
        }
        if(id.equals(ModItems.IRON_CLOUD_BOOTS_ID)) {
            return com.tiviacz.cloudboots.forge.init.ModItems.IRON_CLOUD_BOOTS;
        }
        if(id.equals(ModItems.GOLD_CLOUD_BOOTS_ID)) {
            return com.tiviacz.cloudboots.forge.init.ModItems.GOLD_CLOUD_BOOTS;
        }
        if(id.equals(ModItems.DIAMOND_CLOUD_BOOTS_ID)) {
            return com.tiviacz.cloudboots.forge.init.ModItems.DIAMOND_CLOUD_BOOTS;
        }
        if(id.equals(ModItems.NETHERITE_CLOUD_BOOTS_ID)) {
            return com.tiviacz.cloudboots.forge.init.ModItems.NETHERITE_CLOUD_BOOTS;
        }
        if(id.equals(ModItems.GOLDEN_FEATHER_ID)) {
            return com.tiviacz.cloudboots.forge.init.ModItems.GOLDEN_FEATHER;
        }
        throw new IllegalArgumentException("Unknown item: " + id);
    }

    public static boolean isGoldenFeatherEquipped(LivingEntity livingEntity) {
        AtomicBoolean isEquipped = new AtomicBoolean(false);
        if(CloudBootsForge.curiosLoaded) {
            var cap = livingEntity.getCapability(CuriosCapability.INVENTORY);
            cap.ifPresent(curio -> isEquipped.set(curio.isEquipped(com.tiviacz.cloudboots.forge.init.ModItems.GOLDEN_FEATHER.get())));
        }
        return isEquipped.get();
    }
}