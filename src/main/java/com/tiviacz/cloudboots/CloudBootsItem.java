package com.tiviacz.cloudboots;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
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
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CloudBootsItem extends ArmorItem
{
	private final Multimap<Attribute, AttributeModifier> attributeModifier;
	
	public CloudBootsItem(Properties builder) 
	{
		super(CloudArmorMaterial.CLOUD, EquipmentSlotType.FEET, builder);

		Multimap<Attribute, AttributeModifier> attributeMap = getAttributeModifiers(EquipmentSlotType.FEET);
		ImmutableMultimap.Builder<Attribute, AttributeModifier> modifierBuilder = ImmutableMultimap.builder();
		modifierBuilder.putAll(attributeMap);
		modifierBuilder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier("CloudBootsMovementSpeedModifier", CloudBootsConfigurator.bonusSpeed, Operation.MULTIPLY_TOTAL));
		this.attributeModifier = modifierBuilder.build();
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack)
    {
		return slot == EquipmentSlotType.FEET ? this.attributeModifier : super.getAttributeModifiers(slot, stack);
    }
			
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(entityIn instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity)entityIn;
			
			if(player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == this)
			{
				player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 0, CloudBootsConfigurator.jumpBoost, false, false));
				
				if(!player.isOnGround())
				{
					player.jumpMovementFactor += CloudBootsConfigurator.jumpMovementFactor;
					
					if(player.fallDistance >= 1.0F)
					{
						if(!worldIn.isRemote && worldIn instanceof ServerWorld && CloudBootsConfigurator.spawnParticles)
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

						if(!CloudBootsConfigurator.fallDamage)
						{
							player.fallDistance = 0F;
						}
					}
				}
				
				if(player.isSprinting())
				{
					if(!worldIn.isRemote && worldIn instanceof ServerWorld && CloudBootsConfigurator.spawnParticles)
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
		return super.getIsRepairable(toRepair, repair);
	}

	public static class CloudArmorMaterial implements IArmorMaterial
	{
		public static final CloudArmorMaterial CLOUD = new CloudArmorMaterial();
		
		@Override
		public int getDurability(EquipmentSlotType slotIn) 
		{
			return CloudBootsConfigurator.durability;
		}

		@Override
		public int getDamageReductionAmount(EquipmentSlotType slotIn) 
		{
			return CloudBootsConfigurator.damageReductionAmount;
		}

		@Override
		public int getEnchantability() 
		{
			return CloudBootsConfigurator.enchantability;
		}

		@Override
		public SoundEvent getSoundEvent() 
		{
			return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
		}

		@Override
		public Ingredient getRepairMaterial() 
		{
			return new LazyValue<>(() -> Ingredient.fromItems(Items.GOLD_INGOT, CloudBoots.ModItems.GOLDEN_FEATHER.get())).getValue();
		}

		@Override
		public String getName() 
		{
			return "cloudboots:cloud";
		}

		@Override
		public float getToughness() 
		{
			return CloudBootsConfigurator.toughness;
		}

		@Override
		public float getKnockbackResistance()
		{
			return CloudBootsConfigurator.knockbackResistance;
		}
	}
}