package com.tiviacz.cloudboots.fabric.compat;

import com.tiviacz.cloudboots.fabric.init.ModItems;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class GoldenFeatherTrinket implements Trinket {
    public static void registerTrinket() {
        TrinketsApi.registerTrinket(ModItems.GOLDEN_FEATHER, new GoldenFeatherTrinket());
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if(entity instanceof ServerPlayer serverPlayer) {
            if(serverPlayer.fallDistance >= 3.0F) {
                stack.hurtAndBreak(1, serverPlayer.level(), serverPlayer, e -> onBreak(stack, slot, entity));
            }
        }
    }
}