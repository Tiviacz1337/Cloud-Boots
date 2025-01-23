package com.tiviacz.cloudboots.fabric.compat;

import com.tiviacz.cloudboots.item.GoldenFeatherItem;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class GoldenFeatherTrinket implements Trinket {
    public static void registerTrinket() {
        BuiltInRegistries.ITEM.stream()
                .filter(item -> item instanceof GoldenFeatherItem)
                .forEach(item -> TrinketsApi.registerTrinket(item, new GoldenFeatherTrinket()));
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if(entity instanceof ServerPlayer serverPlayer) {
            if(serverPlayer.fallDistance >= 3.0F) {
                stack.hurtAndBreak(1, serverPlayer.serverLevel(), serverPlayer, e -> onBreak(stack, slot, entity));
                serverPlayer.fallDistance = 0.0F;

                if(!serverPlayer.level().isClientSide && serverPlayer.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.CLOUD, serverPlayer.xOld, serverPlayer.yOld, serverPlayer.zOld, 3, 0, 0, 0, (serverPlayer.level().random.nextFloat() - 0.5F));
                }
            }
        }
    }
}
