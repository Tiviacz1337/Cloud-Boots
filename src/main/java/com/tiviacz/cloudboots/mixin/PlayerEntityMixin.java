package com.tiviacz.cloudboots.mixin;

import com.tiviacz.cloudboots.CloudBootsItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
    @Shadow @Final private PlayerAbilities abilities;

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "getOffGroundSpeed", at = @At(value = "RETURN"), cancellable = true)
    protected void getOffGroundSpeed(CallbackInfoReturnable<Float> cir)
    {
        Item boots = this.getEquippedStack(EquipmentSlot.FEET).getItem();

        if(boots instanceof CloudBootsItem cloudBootsItem)
        {
            if(!(this.abilities.flying && !this.hasVehicle()))
            {
                cir.setReturnValue(this.isSprinting() ? 0.025999999F + (0.01F * cloudBootsItem.getJumpBoostLevel()) : 0.02F + (0.01F * cloudBootsItem.getJumpBoostLevel()));
            }
        }
    }
}
