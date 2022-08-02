package com.tiviacz.cloudboots;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class GoldenFeatherItem extends Item
{
    public GoldenFeatherItem(Settings settings)
    {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(isSelected && entityIn instanceof ServerPlayerEntity serverPlayer)
        {
            if(serverPlayer.fallDistance >= 3.0F)
            {
                stack.damage(1, worldIn.random, serverPlayer);
                serverPlayer.fallDistance = 0.0F;

                if(!worldIn.isClient && worldIn instanceof ServerWorld serverWorld)
                {
                    serverWorld.spawnParticles(ParticleTypes.CLOUD, serverPlayer.lastRenderX, serverPlayer.lastRenderY, serverPlayer.lastRenderZ, 3, 0, 0, 0, (worldIn.random.nextFloat() - 0.5F));
                }
            }
        }
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient)
    {
        return ingredient.getItem() == Items.GOLD_INGOT;
    }
}