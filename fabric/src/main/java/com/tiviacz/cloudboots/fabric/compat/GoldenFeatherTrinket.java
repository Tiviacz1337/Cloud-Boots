package com.tiviacz.cloudboots.fabric.compat;

import com.tiviacz.cloudboots.fabric.init.ModItems;
import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class GoldenFeatherTrinket implements TrinketCallback {
    public static void registerTrinket() {
        TrinketCallback.setCallback(ModItems.GOLDEN_FEATHER, new GoldenFeatherTrinket());
    }

    @Override
    public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
        if(entity instanceof ServerPlayer serverPlayer) {
            if(serverPlayer.fallDistance >= 3.0F) {
                stack.hurtAndBreak(1, serverPlayer.level(), serverPlayer, e -> onBreak(stack, slot, entity));
            }
        }
    }
}