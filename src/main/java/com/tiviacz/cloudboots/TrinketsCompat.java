package com.tiviacz.cloudboots;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class TrinketsCompat
{
    public static void init()
    {
        TrinketsApi.registerTrinket(CloudBoots.ModItems.GOLDEN_FEATHER, new GoldenFeatherTrinket());
    }

    public static class GoldenFeatherTrinket implements Trinket
    {
        @Override
        public void tick(ItemStack stack, SlotReference slot, LivingEntity entity)
        {
            if(entity instanceof ServerPlayerEntity serverPlayer)
            {
                if(serverPlayer.fallDistance >= 3.0F)
                {
                    stack.damage(1, serverPlayer, e -> onBreak(stack, slot, entity));
                    serverPlayer.fallDistance = 0.0F;

                    if(!serverPlayer.world.isClient && serverPlayer.world instanceof ServerWorld serverWorld)
                    {
                        serverWorld.spawnParticles(ParticleTypes.CLOUD, serverPlayer.lastRenderX, serverPlayer.lastRenderY, serverPlayer.lastRenderZ, 3, 0, 0, 0, (serverWorld.random.nextFloat() - 0.5F));
                    }
                }
            }
        }
    }
}
