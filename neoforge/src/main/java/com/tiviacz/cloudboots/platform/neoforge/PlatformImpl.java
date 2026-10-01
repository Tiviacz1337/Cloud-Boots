package com.tiviacz.cloudboots.platform.neoforge;

import com.tiviacz.cloudboots.neoforge.CloudBootsNeoForge;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.api.CuriosCapability;

import java.util.concurrent.atomic.AtomicBoolean;

public class PlatformImpl {
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