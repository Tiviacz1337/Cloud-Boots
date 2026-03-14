package com.tiviacz.cloudboots.mixin;

import com.tiviacz.cloudboots.item.CloudBootsItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    @Shadow
    @Final
    private Abilities abilities;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "getFlyingSpeed", at = @At(value = "RETURN"), cancellable = true)
    protected void getFlyingSpeed(CallbackInfoReturnable<Float> cir) {
        Item boots = this.getItemBySlot(EquipmentSlot.FEET).getItem();

        if(boots instanceof CloudBootsItem cloudBootsItem) {
            if(!(this.abilities.flying && !this.isPassenger())) {
                cir.setReturnValue(this.isSprinting() ? 0.025999999F + (float)cloudBootsItem.getFlyingSpeedModifier() : 0.02F + (float)cloudBootsItem.getFlyingSpeedModifier());
            }
        }
    }
}