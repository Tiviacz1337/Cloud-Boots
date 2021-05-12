package com.tiviacz.cloudboots;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class GoldenFeatherItem extends Item
{
	public GoldenFeatherItem(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(isSelected && entityIn instanceof ServerPlayerEntity)
		{
			if(entityIn.fallDistance >= 3.0F)
			{
				stack.attemptDamageItem(1, worldIn.rand, (ServerPlayerEntity)entityIn);
				entityIn.fallDistance = 0.0F;
				
				if(!worldIn.isRemote && worldIn instanceof ServerWorld && CloudBootsConfigurator.goldenFeatherSpawnParticles)
				{
					((ServerWorld)worldIn).spawnParticle(ParticleTypes.CLOUD, entityIn.prevPosX, entityIn.prevPosY, entityIn.prevPosZ, 3, 0, 0, 0, (worldIn.rand.nextFloat() - 0.5F));
				}
				
		/*		for(int i = 0; i < 3; i++)
				{
					worldIn.spawnParticle(EnumParticleTypes.CLOUD, entityIn.posX, entityIn.posY, entityIn.posZ, (itemRand.nextFloat() - 0.5F), -0.5D, (itemRand.nextFloat() - 0.5F));
				}   */
			}
		}
    }
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
		return repair.getItem() == Items.GOLD_INGOT;
	}
}