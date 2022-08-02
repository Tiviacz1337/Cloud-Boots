package com.tiviacz.cloudboots;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;
import net.minecraft.world.World;

public class CloudBootsItem extends ArmorItem
{
    public CloudBootsItem(Settings settings)
    {
        super(CloudArmorMaterial.CLOUD, EquipmentSlot.FEET, settings);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient)
    {
        return super.canRepair(stack, ingredient);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(entityIn instanceof PlayerEntity player)
        {
            if(player.getEquippedStack(EquipmentSlot.FEET).getItem() == this)
            {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 0, 4, false, false));

                if(!player.isOnGround())
                {
                    player.flyingSpeed += 0.012D;

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