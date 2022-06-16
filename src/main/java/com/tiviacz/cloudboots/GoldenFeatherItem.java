package com.tiviacz.cloudboots;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
				stack.hurt(1, worldIn.random, (ServerPlayerEntity)entityIn);
				entityIn.fallDistance = 0.0F;
				
				if(!worldIn.isClientSide && worldIn instanceof ServerWorld)
				{
					((ServerWorld)worldIn).sendParticles(ParticleTypes.CLOUD, entityIn.xOld, entityIn.yOld, entityIn.zOld, 3, 0, 0, 0, (worldIn.random.nextFloat() - 0.5F));
				}
				
		/*		for(int i = 0; i < 3; i++)
				{
					worldIn.spawnParticle(EnumParticleTypes.CLOUD, entityIn.posX, entityIn.posY, entityIn.posZ, (itemRand.nextFloat() - 0.5F), -0.5D, (itemRand.nextFloat() - 0.5F));
				}   */
			}
		}
    }
	
	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair)
    {
		return repair.getItem() == Items.GOLD_INGOT;
	}

	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt)
	{
		if(CloudBoots.enableCurios())
		{
			return new ICapabilityProvider()
			{
				final LazyOptional<ICurio> curio = LazyOptional.of(() -> GoldenFeatherCurio.createGoldenFeatherCurioProvider(stack));

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
				{
					return CuriosCapability.ITEM.orEmpty(cap, curio);
				}
			};
		}
		return null;
	}
}