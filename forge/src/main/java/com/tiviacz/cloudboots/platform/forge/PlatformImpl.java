package com.tiviacz.cloudboots.platform.forge;

import com.tiviacz.cloudboots.forge.CloudBootsForge;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.api.CuriosCapability;

import java.util.concurrent.atomic.AtomicBoolean;

public class PlatformImpl {
    public static boolean isGoldenFeatherEquipped(LivingEntity livingEntity) {
        AtomicBoolean isEquipped = new AtomicBoolean(false);
        if(CloudBootsForge.curiosLoaded) {
            var cap = livingEntity.getCapability(CuriosCapability.INVENTORY);
            cap.ifPresent(curio -> isEquipped.set(curio.isEquipped(com.tiviacz.cloudboots.forge.init.ModItems.GOLDEN_FEATHER.get())));
        }
        return isEquipped.get();
    }
}