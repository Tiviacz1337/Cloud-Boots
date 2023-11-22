package com.tiviacz.cloudboots;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GoldenFeatherItem extends Item
{
    public GoldenFeatherItem(Settings settings)
    {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(entityIn instanceof ServerPlayerEntity)
        {
            if(((ServerPlayerEntity)entityIn).getStackInHand(Hand.MAIN_HAND).getItem() == stack.getItem())
            {
                if(entityIn.fallDistance >= 3.0F)
                {
                    ((ServerPlayerEntity)entityIn).getStackInHand(Hand.MAIN_HAND).damage(1, (ServerPlayerEntity)entityIn, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                    entityIn.fallDistance = 0.0F;

                    if(!worldIn.isClient && worldIn instanceof ServerWorld)
                    {
                        ((ServerWorld)worldIn).spawnParticles(ParticleTypes.CLOUD, entityIn.lastRenderX, entityIn.lastRenderY, entityIn.lastRenderZ, 3, 0, 0, 0, (worldIn.random.nextFloat() - 0.5F));
                    }
                }
            }

            else if(((ServerPlayerEntity)entityIn).getStackInHand(Hand.OFF_HAND).getItem() == stack.getItem())
            {
                if(entityIn.fallDistance >= 3.0F)
                {
                    ((ServerPlayerEntity)entityIn).getStackInHand(Hand.OFF_HAND).damage(1, (ServerPlayerEntity)entityIn, e -> e.sendEquipmentBreakStatus(EquipmentSlot.OFFHAND));
                    entityIn.fallDistance = 0.0F;

                    if(!worldIn.isClient && worldIn instanceof ServerWorld)
                    {
                        ((ServerWorld)worldIn).spawnParticles(ParticleTypes.CLOUD, entityIn.lastRenderX, entityIn.lastRenderY, entityIn.lastRenderZ, 3, 0, 0, 0, (worldIn.random.nextFloat() - 0.5F));
                    }
                }
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(new TranslatableText("item.cloudboots.negates_fall_damage").formatted(Formatting.BLUE));
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient)
    {
        return ingredient.getItem() == Items.GOLD_INGOT;
    }
}