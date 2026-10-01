package com.tiviacz.cloudboots.platform.fabric;

import com.tiviacz.cloudboots.fabric.CloudBootsFabric;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.LivingEntity;

import java.util.concurrent.atomic.AtomicBoolean;

public class PlatformImpl {
    public static boolean isGoldenFeatherEquipped(LivingEntity livingEntity) {
        AtomicBoolean isEquipped = new AtomicBoolean(false);
        if(CloudBootsFabric.trinketsLoaded) {
            TrinketsApi.getTrinketComponent(livingEntity).ifPresent(trinkets -> isEquipped.set(trinkets.isEquipped(com.tiviacz.cloudboots.fabric.init.ModItems.GOLDEN_FEATHER)));
        }
        return isEquipped.get();
    }
}