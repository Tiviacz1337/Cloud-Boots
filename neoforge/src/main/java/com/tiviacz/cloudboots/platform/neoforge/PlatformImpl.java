package com.tiviacz.cloudboots.platform.neoforge;

import com.tiviacz.cloudboots.init.ModItems;
import com.tiviacz.cloudboots.neoforge.CloudBootsNeoForge;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosCapability;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class PlatformImpl {
    public static Supplier<Item> getItem(Identifier id) {
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

    public static boolean isGoldenFeatherEquipped(LivingEntity livingEntity) {
        AtomicBoolean isEquipped = new AtomicBoolean(false);
        if(CloudBootsNeoForge.curiosLoaded) {
            var cap = livingEntity.getCapability(CuriosCapability.INVENTORY);
            if(cap != null) {
                isEquipped.set(cap.isEquipped(com.tiviacz.cloudboots.neoforge.init.ModItems.GOLDEN_FEATHER.get()));
            }
        }
        return isEquipped.get();
    }
}