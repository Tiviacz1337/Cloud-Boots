package com.tiviacz.cloudboots.platform.fabric;

import com.tiviacz.cloudboots.fabric.CloudBootsFabric;
import com.tiviacz.cloudboots.item.GoldenFeatherItem;
import eu.pb4.trinkets.api.TrinketAttachment;
import eu.pb4.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.LivingEntity;

import java.util.concurrent.atomic.AtomicBoolean;

public class PlatformImpl {
    public static boolean isGoldenFeatherEquipped(LivingEntity livingEntity) {
        AtomicBoolean isEquipped = new AtomicBoolean(false);
        if(CloudBootsFabric.trinketsLoaded) {
            TrinketAttachment attachment = TrinketsApi.getAttachment(livingEntity);
            if(attachment.isEquipped(p -> p.getItem() instanceof GoldenFeatherItem)) {
                isEquipped.set(true);
            }
        }
        return isEquipped.get();
    }
}