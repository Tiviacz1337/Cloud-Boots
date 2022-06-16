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

		Multimap<Attribute, AttributeModifier> attributeMap = getDefaultAttributeModifiers(EquipmentSlotType.FEET);
		ImmutableMultimap.Builder<Attribute, AttributeModifier> modifierBuilder = ImmutableMultimap.builder();
		modifierBuilder.putAll(attributeMap);
		modifierBuilder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier("CloudBootsMovementSpeedModifier", 0.15D, Operation.MULTIPLY_TOTAL));
		this.attributeModifier = modifierBuilder.build();
	}

	@Override
	public IArmorMaterial getMaterial()
	{
		return CloudArmorMaterial.CLOUD;
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
			
			if(player.getItemBySlot(EquipmentSlotType.FEET).getItem() == this)
			{
				player.addEffect(new EffectInstance(Effects.JUMP, 0, 4, false, false));
				
				if(!player.isOnGround())
				{
					player.flyingSpeed += 0.012D;
					
					if(player.fallDistance >= 1.0F)
					{
						if(!worldIn.isClientSide && worldIn instanceof ServerWorld)
						{
							((ServerWorld)worldIn).sendParticles(ParticleTypes.CLOUD, player.xOld, player.yOld, player.zOld, 3, 0, 0, 0, (random.nextFloat() - 0.5F));
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
					if(!worldIn.isClientSide && worldIn instanceof ServerWorld)
					{
						((ServerWorld)worldIn).sendParticles(ParticleTypes.CLOUD, player.xOld, player.yOld, player.zOld, 1, 0, 0, 0, (random.nextFloat() - 0.5F));
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

	public static class CloudArmorMaterial implements IArmorMaterial
	{
		public static final CloudArmorMaterial CLOUD = new CloudArmorMaterial();
		
		@Override
		public int getDurabilityForSlot(EquipmentSlotType slotIn)
		{
			return 1337;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slotIn)
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
			return new LazyValue<>(() -> Ingredient.of(Items.GOLD_INGOT, CloudBoots.ModItems.GOLDEN_FEATHER.get())).get();
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