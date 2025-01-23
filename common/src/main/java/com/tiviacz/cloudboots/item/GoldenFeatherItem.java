package com.tiviacz.cloudboots.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class GoldenFeatherItem extends Item {
    public GoldenFeatherItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entityIn, int itemSlot, boolean isSelected) {
        if(entityIn instanceof ServerPlayer serverPlayer) {
            if(serverPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() == stack.getItem()) {
                if(serverPlayer.fallDistance >= 3.0F) {
                    serverPlayer.getItemInHand(InteractionHand.MAIN_HAND).hurtAndBreak(1, serverPlayer, EquipmentSlot.MAINHAND);
                    serverPlayer.fallDistance = 0.0F;

                    if(!level.isClientSide && level instanceof ServerLevel server) {
                        server.sendParticles(ParticleTypes.CLOUD, serverPlayer.xo, serverPlayer.yo, serverPlayer.zo, 3, 0, 0, 0, (level.random.nextFloat() - 0.5F));
                    }
                }
            } else if(serverPlayer.getItemInHand(InteractionHand.OFF_HAND).getItem() == stack.getItem()) {
                if(serverPlayer.fallDistance >= 3.0F) {
                    serverPlayer.getItemInHand(InteractionHand.OFF_HAND).hurtAndBreak(1, serverPlayer, EquipmentSlot.OFFHAND);
                    serverPlayer.fallDistance = 0.0F;

                    if(!level.isClientSide && level instanceof ServerLevel server) {
                        server.sendParticles(ParticleTypes.CLOUD, serverPlayer.xo, serverPlayer.yo, serverPlayer.zo, 3, 0, 0, 0, (level.random.nextFloat() - 0.5F));
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("item.cloudboots.negates_fall_damage").withStyle(ChatFormatting.BLUE));
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == Items.GOLD_INGOT;
    }
}