package com.tiviacz.cloudboots;

import com.google.common.collect.Multimap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CloudBootsItem extends ArmorItem
{
	private AttributeModifier movementSpeed;
	
	public CloudBootsItem(Properties builder) 
	{
		super(CloudArmorMaterial.cloud, EquipmentSlotType.FEET, builder);
		
		this.movementSpeed = new AttributeModifier("CloudBootsMovementSpeedModifier", 0.15F, Operation.MULTIPLY_TOTAL);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack)
    {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);
		
		if(slot == EquipmentSlotType.FEET && stack.getItem() == this)
		{
			multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), this.movementSpeed);
		}
		return multimap;
    }
			
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(entityIn instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity)entityIn;
			
			if(player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == this)
			{
				player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 0, 4, false, false));
				
				if(!player.onGround)
				{
					player.jumpMovementFactor += 0.03F;
					
					if(player.fallDistance >= 1.0F)
					{
						if(!worldIn.isRemote && worldIn instanceof ServerWorld)
						{
							((ServerWorld)worldIn).spawnParticle(ParticleTypes.CLOUD, player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ, 3, 0, 0, 0, (random.nextFloat() - 0.5F));
						}
						
					/*	else if(world.isRemote)
						{
							for(int i = 0; i < 3; i++)
							{
								world.spawnParticle(EnumParticleTypes.CLOUD, player.posX, player.posY, player.posZ, (itemRand.nextFloat() - 0.5F), -0.5D, (itemRand.nextFloat() - 0.5F));
							}
						} */
							
						player.fallDistance = 0F;
					}
				}
				
				if(player.isSprinting())
				{
					if(!worldIn.isRemote && worldIn instanceof ServerWorld)
					{
						((ServerWorld)worldIn).spawnParticle(ParticleTypes.CLOUD, player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ, 1, 0, 0, 0, (random.nextFloat() - 0.5F));
					}
	
				/*	else if(world.isRemote)
					{
						world.spawnParticle(EnumParticleTypes.CLOUD, player.posX, player.posY, player.posZ, (itemRand.nextFloat() - 0.5F), -0.5D, (itemRand.nextFloat() - 0.5F));
					} */
				}
			}
		}
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        if(repair.getItem() == Items.GOLD_INGOT)
        {
        	return true;
        }
        return false;
    }
	
	public static class CloudArmorMaterial implements IArmorMaterial
	{
		public static CloudArmorMaterial cloud = new CloudArmorMaterial("cloud", 1337, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
		
		private SoundEvent equipSound;
		private String name;
		private int durability, enchantability;
		private final int[] damageReduction;
		private float toughness;
		
		private CloudArmorMaterial(String name, int durability, int[] damageReduction, int enchantability, SoundEvent equipSound, float toughness) 
		{
			this.name = name;
			this.equipSound = equipSound;
			this.durability = durability;
			this.enchantability = enchantability;
			this.damageReduction = damageReduction;
			this.toughness = toughness;
		}
		
		@Override
		public int getDurability(EquipmentSlotType slotIn) 
		{
			return this.durability;
		}

		@Override
		public int getDamageReductionAmount(EquipmentSlotType slotIn) 
		{
			return this.damageReduction[slotIn.getIndex()];
		}

		@Override
		public int getEnchantability() 
		{
			return this.enchantability;
		}

		@Override
		public SoundEvent getSoundEvent() 
		{
			return this.equipSound;
		}

		@Override
		public Ingredient getRepairMaterial() 
		{
			return null;
		}

		@Override
		public String getName() 
		{
			return "cloudboots:" + this.name;
		}

		@Override
		public float getToughness() 
		{
			return this.toughness;
		}
	}
}