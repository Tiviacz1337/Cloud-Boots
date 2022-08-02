package com.tiviacz.cloudboots;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
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
	public void inventoryTick(ItemStack stack, Level level, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if(isSelected && entityIn instanceof ServerPlayer serverPlayer)
		{
			if(serverPlayer.fallDistance >= 3.0F)
			{
				stack.hurt(1, level.random, serverPlayer);
				serverPlayer.fallDistance = 0.0F;
				
				if(!level.isClientSide && level instanceof ServerLevel serverLevel)
				{
					serverLevel.sendParticles(ParticleTypes.CLOUD, serverPlayer.xo, serverPlayer.yo, serverPlayer.zo, 3, 0, 0, 0, (level.random.nextFloat() - 0.5F));
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
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
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