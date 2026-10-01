package com.tiviacz.cloudboots.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.LivingEntity;

public class Platform {
    @ExpectPlatform
    public static boolean isGoldenFeatherEquipped(LivingEntity livingEntity) {
        return false;
    }
}