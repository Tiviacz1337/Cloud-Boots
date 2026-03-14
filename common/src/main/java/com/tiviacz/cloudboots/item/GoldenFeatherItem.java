package com.tiviacz.cloudboots.item;

import com.tiviacz.cloudboots.config.CloudBootsConfig;
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
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class GoldenFeatherItem extends Item {
    public GoldenFeatherItem(Properties properties) {
        super(properties.stacksTo(1).durability(385).repairable(Items.GOLD_INGOT));
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
        if(entity instanceof ServerPlayer serverPlayer) {
            if(serverPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() == stack.getItem()) {
                if(serverPlayer.fallDistance >= 3.0F) {
                    serverPlayer.getItemInHand(InteractionHand.MAIN_HAND).hurtAndBreak(1, serverPlayer, EquipmentSlot.MAINHAND);
                    serverPlayer.fallDistance = 0.0F;
                    spawnParticles(level, serverPlayer);
                }
            } else if(serverPlayer.getItemInHand(InteractionHand.OFF_HAND).getItem() == stack.getItem()) {
                if(serverPlayer.fallDistance >= 3.0F) {
                    serverPlayer.getItemInHand(InteractionHand.OFF_HAND).hurtAndBreak(1, serverPlayer, EquipmentSlot.OFFHAND);
                    serverPlayer.fallDistance = 0.0F;
                    spawnParticles(level, serverPlayer);
                }
            }
        }
        super.inventoryTick(stack, level, entity, slot);
    }

    public static void spawnParticles(Level level, ServerPlayer serverPlayer) {
        if(CloudBootsConfig.SERVER.spawnParticles.get()) {
            if(!level.isClientSide && level instanceof ServerLevel server && level.random.nextFloat() > 0.5F) {
                server.sendParticles(ParticleTypes.CLOUD, serverPlayer.xo, serverPlayer.yo, serverPlayer.zo, 1, 0, 0, 0, (level.random.nextFloat() - 0.5F));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.cloudboots.negates_fall_damage").withStyle(ChatFormatting.BLUE));
    }
}