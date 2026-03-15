package com.tiviacz.cloudboots.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class GoldenFeatherItem extends Item {
    public GoldenFeatherItem(Properties properties) {
        super(properties.stacksTo(1).durability(385).repairable(Items.GOLD_INGOT));
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
        if(entity instanceof ServerPlayer serverPlayer) {
            if(slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) {
                if(serverPlayer.getItemBySlot(slot).getItem() == stack.getItem()) {
                    if(serverPlayer.fallDistance >= 3.0F) {
                        serverPlayer.getItemBySlot(slot).hurtAndBreak(1, serverPlayer, slot);
                    }
                }
            }
        }
        super.inventoryTick(stack, level, entity, slot);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.cloudboots.negates_fall_damage").withStyle(ChatFormatting.BLUE));
    }
}