package com.tiviacz.cloudboots;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CloudBootsItem extends ArmorItem
{
	private final Multimap<Attribute, AttributeModifier> attributeModifier;
	private final int jumpBoostLevel;

	public CloudBootsItem(ArmorMaterial material, double speedModifier, int jumpBoostLevel)
	{
		super(new DefaultArmorMaterial(material), Type.BOOTS, new Item.Properties());

		this.jumpBoostLevel = jumpBoostLevel;

		Multimap<Attribute, AttributeModifier> attributeMap = getDefaultAttributeModifiers(EquipmentSlot.FEET);
		ImmutableMultimap.Builder<Attribute, AttributeModifier> modifierBuilder = ImmutableMultimap.builder();
		modifierBuilder.putAll(attributeMap);
		modifierBuilder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier("CloudBootsMovementSpeedModifier", speedModifier, AttributeModifier.Operation.MULTIPLY_TOTAL));
		this.attributeModifier = modifierBuilder.build();
	}

	public int getJumpBoostLevel()
	{
		return this.jumpBoostLevel;
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
				player.addEffect(new MobEffectInstance(MobEffects.JUMP, 0, this.jumpBoostLevel, false, false));
				
				if(!player.onGround())
				{
					if(player.fallDistance >= 1.0F)
					{
						if(!level.isClientSide && level instanceof ServerLevel server)
						{
							server.sendParticles(ParticleTypes.CLOUD, player.xo, player.yo, player.zo, 3, 0, 0, 0, (level.random.nextFloat() - 0.5F));
						}
						player.fallDistance = 0F;
					}
				}
				
				if(player.isSprinting())
				{
					if(!level.isClientSide && level instanceof ServerLevel server)
					{
						server.sendParticles(ParticleTypes.CLOUD, player.xo, player.yo, player.zo, 1, 0, 0, 0, (level.random.nextFloat() - 0.5F));
					}
				}
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> componentList, TooltipFlag isAdvanced)
	{
		super.appendHoverText(stack, level, componentList, isAdvanced);

		if(stack.getItem() == CloudBoots.ModItems.CLOUD_BOOTS.get())
		{
			componentList.add(Component.translatable("item.cloudboots.og_boots").withStyle(ChatFormatting.BLUE));
		}

		componentList.add(Component.translatable("item.cloudboots.negates_fall_damage").withStyle(ChatFormatting.BLUE));
		MutableComponent mutablecomponent = Component.translatable(MobEffects.JUMP.getDescriptionId());
		mutablecomponent = Component.translatable("potion.withAmplifier", mutablecomponent, Component.translatable("potion.potency." + this.jumpBoostLevel)).withStyle(ChatFormatting.BLUE);
		componentList.add(mutablecomponent);
	}

	public static class DefaultArmorMaterial implements ArmorMaterial
	{
		private final ArmorMaterial defaultMaterial;

		public DefaultArmorMaterial(ArmorMaterial defaultMaterial)
		{
			this.defaultMaterial = defaultMaterial;
		}

		@Override
		public int getDurabilityForType(Type type)
		{
			return defaultMaterial.getDurabilityForType(type);
		}

		@Override
		public int getDefenseForType(Type type)
		{
			return defaultMaterial.getDefenseForType(type);
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

	public static class CloudArmorMaterial implements ArmorMaterial
	{
		public static final CloudArmorMaterial CLOUD = new CloudArmorMaterial();
		
		@Override
		public int getDurabilityForType(Type type)
		{
			return 1337;
		}

		@Override
		public int getDefenseForType(Type type)
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