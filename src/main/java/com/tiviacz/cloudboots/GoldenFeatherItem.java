package com.tiviacz.cloudboots;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class GoldenFeatherItem extends Item
{
	public GoldenFeatherItem(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(entityIn instanceof ServerPlayerEntity)
		{
			if(((ServerPlayerEntity)entityIn).getItemInHand(Hand.MAIN_HAND).getItem() == stack.getItem())
			{
				if(entityIn.fallDistance >= 3.0F)
				{
					((ServerPlayerEntity)entityIn).getItemInHand(Hand.MAIN_HAND).hurtAndBreak(1, (ServerPlayerEntity)entityIn, e -> e.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
					entityIn.fallDistance = 0.0F;

					if(!worldIn.isClientSide && worldIn instanceof ServerWorld)
					{
						((ServerWorld)worldIn).sendParticles(ParticleTypes.CLOUD, entityIn.xOld, entityIn.yOld, entityIn.zOld, 3, 0, 0, 0, (worldIn.random.nextFloat() - 0.5F));
					}
				}
			}

			else if(((ServerPlayerEntity)entityIn).getItemInHand(Hand.OFF_HAND).getItem() == stack.getItem())
			{
				if(entityIn.fallDistance >= 3.0F)
				{
					((ServerPlayerEntity)entityIn).getItemInHand(Hand.OFF_HAND).hurtAndBreak(1, (ServerPlayerEntity)entityIn, e -> e.broadcastBreakEvent(EquipmentSlotType.OFFHAND));
					entityIn.fallDistance = 0.0F;

					if(!worldIn.isClientSide && worldIn instanceof ServerWorld)
					{
						((ServerWorld)worldIn).sendParticles(ParticleTypes.CLOUD, entityIn.xOld, entityIn.yOld, entityIn.zOld, 3, 0, 0, 0, (worldIn.random.nextFloat() - 0.5F));
					}
				}
			}
		}
    }

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World level, List<ITextComponent> tooltip, ITooltipFlag flag)
	{
		super.appendHoverText(stack, level, tooltip, flag);
		tooltip.add(new TranslationTextComponent("item.cloudboots.negates_fall_damage").withStyle(TextFormatting.BLUE));
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