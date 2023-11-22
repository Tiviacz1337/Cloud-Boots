package com.tiviacz.cloudboots;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class CloudBootsItem extends ArmorItem
{
	private final Multimap<Attribute, AttributeModifier> attributeModifier;
	private final int jumpBoostLevel;
	
	public CloudBootsItem(IArmorMaterial material, double speedModifier, int jumpBoostLevel)
	{
		super(new DefaultArmorMaterial(material), EquipmentSlotType.FEET, new Item.Properties().tab(ItemGroup.TAB_COMBAT));

		this.jumpBoostLevel = jumpBoostLevel;

		Multimap<Attribute, AttributeModifier> attributeMap = getDefaultAttributeModifiers(EquipmentSlotType.FEET);
		ImmutableMultimap.Builder<Attribute, AttributeModifier> modifierBuilder = ImmutableMultimap.builder();
		modifierBuilder.putAll(attributeMap);
		modifierBuilder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier("CloudBootsMovementSpeedModifier", speedModifier, Operation.MULTIPLY_TOTAL));
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
			
			if(player.getItemBySlot(EquipmentSlotType.FEET).getItem() == this)
			{
				player.addEffect(new EffectInstance(Effects.JUMP, 0, this.jumpBoostLevel, false, false));
				
				if(!player.isOnGround())
				{
					player.flyingSpeed = (player.isSprinting() ? 0.026F : 0.02F) + (0.01F * this.jumpBoostLevel);
					
					if(player.fallDistance >= 1.0F)
					{
						if(!worldIn.isClientSide && worldIn instanceof ServerWorld)
						{
							((ServerWorld)worldIn).sendParticles(ParticleTypes.CLOUD, player.xOld, player.yOld, player.zOld, 3, 0, 0, 0, (random.nextFloat() - 0.5F));
						}
						player.fallDistance = 0F;
					}
				}
				
				if(player.isSprinting())
				{
					if(!worldIn.isClientSide && worldIn instanceof ServerWorld)
					{
						((ServerWorld)worldIn).sendParticles(ParticleTypes.CLOUD, player.xOld, player.yOld, player.zOld, 1, 0, 0, 0, (random.nextFloat() - 0.5F));
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
		if(stack.getItem() == CloudBoots.ModItems.CLOUD_BOOTS.get())
		{
			tooltip.add(new TranslationTextComponent("item.cloudboots.og_boots").withStyle(TextFormatting.BLUE));
		}
		tooltip.add(new TranslationTextComponent("item.cloudboots.negates_fall_damage").withStyle(TextFormatting.BLUE));
		IFormattableTextComponent formattableTextComponent = new TranslationTextComponent(Effects.JUMP.getDescriptionId());
		formattableTextComponent = new TranslationTextComponent("potion.withAmplifier", formattableTextComponent, new TranslationTextComponent("potion.potency." + this.jumpBoostLevel)).withStyle(TextFormatting.BLUE);
		tooltip.add(formattableTextComponent);
	}

	public static class DefaultArmorMaterial implements IArmorMaterial
	{
		private final IArmorMaterial defaultMaterial;

		public DefaultArmorMaterial(IArmorMaterial defaultMaterial)
		{
			this.defaultMaterial = defaultMaterial;
		}

		@Override
		public int getDurabilityForSlot(EquipmentSlotType pSlot)
		{
			return defaultMaterial.getDurabilityForSlot(pSlot);
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType pSlot)
		{
			return defaultMaterial.getDefenseForSlot(pSlot);
		}

		@Override
		public int getEnchantmentValue()
		{
			return defaultMaterial.getEnchantmentValue();
		}

		@Override
		public SoundEvent getEquipSound()
		{
			return defaultMaterial.getEquipSound();
		}

		@Override
		public Ingredient getRepairIngredient()
		{
			return defaultMaterial.getRepairIngredient();
		}

		@Override
		public String getName()
		{
			if(defaultMaterial == CloudArmorMaterial.CLOUD)
			{
				return CloudArmorMaterial.CLOUD.getName();
			}
			return "cloudboots:" + defaultMaterial.getName() + "_cloud";
		}

		@Override
		public float getToughness()
		{
			return defaultMaterial.getToughness();
		}

		@Override
		public float getKnockbackResistance()
		{
			return defaultMaterial.getKnockbackResistance();
		}
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