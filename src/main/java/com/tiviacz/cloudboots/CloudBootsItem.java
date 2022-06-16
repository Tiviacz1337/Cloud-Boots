package com.tiviacz.cloudboots;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class CloudBootsItem extends ArmorItem
{
	private final Multimap<Attribute, AttributeModifier> attributeModifier;
	
	public CloudBootsItem(Properties builder) 
	{
		super(CloudArmorMaterial.CLOUD, EquipmentSlot.FEET, builder);

		Multimap<Attribute, AttributeModifier> attributeMap = getDefaultAttributeModifiers(EquipmentSlot.FEET);
		ImmutableMultimap.Builder<Attribute, AttributeModifier> modifierBuilder = ImmutableMultimap.builder();
		modifierBuilder.putAll(attributeMap);
		modifierBuilder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier("CloudBootsMovementSpeedModifier", 0.15D, AttributeModifier.Operation.MULTIPLY_TOTAL));
		this.attributeModifier = modifierBuilder.build();
	}

	@Override
	public ArmorMaterial getMaterial()
	{
		return CloudArmorMaterial.CLOUD;
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
		return slot == EquipmentSlot.FEET ? this.attributeModifier : super.getAttributeModifiers(slot, stack);
    }
			
	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if(entityIn instanceof Player player)
		{
			if(player.getItemBySlot(EquipmentSlot.FEET).getItem() == this)
			{
				player.addEffect(new MobEffectInstance(MobEffects.JUMP, 0, 4, false, false));
				
				if(!player.isOnGround())
				{
					player.flyingSpeed += 0.012D;
					
					if(player.fallDistance >= 1.0F)
					{
						if(!level.isClientSide && level instanceof ServerLevel server)
						{
							server.sendParticles(ParticleTypes.CLOUD, player.xo, player.yo, player.zo, 3, 0, 0, 0, (level.random.nextFloat() - 0.5F));
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
					if(!level.isClientSide && level instanceof ServerLevel server)
					{
						server.sendParticles(ParticleTypes.CLOUD, player.xo, player.yo, player.zo, 1, 0, 0, 0, (level.random.nextFloat() - 0.5F));
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
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair)
	{
		return super.isValidRepairItem(toRepair, repair);
	}

	public static class CloudArmorMaterial implements ArmorMaterial
	{
		public static final CloudArmorMaterial CLOUD = new CloudArmorMaterial();
		
		@Override
		public int getDurabilityForSlot(EquipmentSlot slotIn)
		{
			return 1337;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlot slotIn)
		{
			return 3;
		}

		@Override
		public int getEnchantmentValue()
		{
			return 10;
		}

		@Override
		public SoundEvent getEquipSound()
		{
			return SoundEvents.ARMOR_EQUIP_DIAMOND;
		}

		@Override
		public Ingredient getRepairIngredient()
		{
			return new LazyLoadedValue<>(() -> Ingredient.of(Items.GOLD_INGOT, CloudBoots.ModItems.GOLDEN_FEATHER.get())).get();
		}

		@Override
		public String getName() 
		{
			return "cloudboots:cloud";
		}

		@Override
		public float getToughness() 
		{
			return 2.0F;
		}

		@Override
		public float getKnockbackResistance()
		{
			return 0.0F;
		}
	}
}