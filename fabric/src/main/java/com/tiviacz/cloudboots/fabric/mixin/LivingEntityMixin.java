package com.tiviacz.cloudboots.fabric.mixin;

import com.tiviacz.cloudboots.item.CloudBootsItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At(value = "HEAD"), method = "hurtServer", cancellable = true)
    private void playerFallDamage(ServerLevel level, DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(CloudBootsItem.negateFallDamage((LivingEntity)(Object)this, damageSource)) {
            cir.setReturnValue(false);
        }
    }
}