package com.tiviacz.cloudboots;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Lazy;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CloudBootsItem extends ArmorItem
{
    private final int jumpBoostLevel;

    public CloudBootsItem(ArmorMaterial armorMaterial, double speedModifier, int jumpBoostLevel)
    {
        super(new DefaultArmorMaterial(armorMaterial), EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

        this.jumpBoostLevel = jumpBoostLevel;

        Multimap<EntityAttribute, EntityAttributeModifier> attributeMap = getAttributeModifiers(EquipmentSlot.FEET);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> modifierBuilder = ImmutableMultimap.builder();
        modifierBuilder.putAll(attributeMap);
        modifierBuilder.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("CloudBootsMovementSpeedModifier", speedModifier, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        this.attributeModifiers = modifierBuilder.build();
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(entityIn instanceof PlayerEntity player)
        {
            if(player.getEquippedStack(EquipmentSlot.FEET).getItem() == this)
            {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 0, this.jumpBoostLevel, false, false));

                if(!player.isOnGround())
                {
                    player.airStrafingSpeed = (player.isSprinting() ? 0.026F : 0.02F) + (0.01F * this.jumpBoostLevel);

                    if(player.fallDistance >= 1.0F)
                    {
                        if(!worldIn.isClient && worldIn instanceof ServerWorld serverWorld)
                        {
                            serverWorld.spawnParticles(ParticleTypes.CLOUD, player.lastRenderX, player.lastRenderY, player.lastRenderZ, 3, 0, 0, 0, (worldIn.random.nextFloat() - 0.5F));
                        }

                        player.fallDistance = 0F;
                    }
                }

                if(player.isSprinting())
                {
                    if(!worldIn.isClient && worldIn instanceof ServerWorld serverWorld)
                    {
                        serverWorld.spawnParticles(ParticleTypes.CLOUD, player.lastRenderX, player.lastRenderY, player.lastRenderZ, 1, 0, 0, 0, (worldIn.random.nextFloat() - 0.5F));
                    }
                }
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);

        if(stack.getItem() == CloudBoots.ModItems.CLOUD_BOOTS)
        {
            tooltip.add(new TranslatableText("item.cloudboots.og_boots").formatted(Formatting.BLUE));
        }
        tooltip.add(new TranslatableText("item.cloudboots.negates_fall_damage").formatted(Formatting.BLUE));
        MutableText mutablecomponent = new TranslatableText(StatusEffects.JUMP_BOOST.getTranslationKey());
        mutablecomponent = new TranslatableText("potion.withAmplifier", mutablecomponent, new TranslatableText("potion.potency." + this.jumpBoostLevel)).formatted(Formatting.BLUE);
        tooltip.add(mutablecomponent);
    }

    public static class DefaultArmorMaterial implements ArmorMaterial
    {
        private final ArmorMaterial defaultMaterial;

        public DefaultArmorMaterial(ArmorMaterial defaultMaterial)
        {
            this.defaultMaterial = defaultMaterial;
        }

        @Override
        public int getDurability(EquipmentSlot slot)
        {
            return defaultMaterial.getDurability(slot);
        }

        @Override
        public int getProtectionAmount(EquipmentSlot slot)
        {
            return defaultMaterial.getProtectionAmount(slot);
        }

        @Override
        public int getEnchantability()
        {
            return defaultMaterial.getEnchantability();
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
            return defaultMaterial.getName() + "_cloud";
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
        public int getDurability(EquipmentSlot slot)
        {
            return 1337;
        }

        @Override
        public int getProtectionAmount(EquipmentSlot slot)
        {
            return 3;
        }

        @Override
        public int getEnchantability()
        {
            return 10;
        }

        @Override
        public SoundEvent getEquipSound()
        {
            return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
        }

        @Override
        public Ingredient getRepairIngredient()
        {
            return new Lazy<>(() -> Ingredient.ofItems(Items.GOLD_INGOT, CloudBoots.ModItems.GOLDEN_FEATHER)).get();
        }

        @Override
        public String getName()
        {
            return "cloud";
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