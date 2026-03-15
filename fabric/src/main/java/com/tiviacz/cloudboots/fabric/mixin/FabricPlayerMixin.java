package com.tiviacz.cloudboots.fabric.mixin;

import com.tiviacz.cloudboots.item.CloudBootsItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class FabricPlayerMixin extends LivingEntity {
    protected FabricPlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At(value = "TAIL"), method = "tick")
    private void playerTick(CallbackInfo info) {
        CloudBootsItem.tick((Player)(Object)this);
    }
}