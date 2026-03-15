package com.tiviacz.cloudboots.fabric.mixin;

import com.tiviacz.cloudboots.item.CloudBootsItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.TooltipDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackTooltipMixin {
    @Inject(method = "addAttributeTooltips", at = @At(value = "TAIL"))
    protected void addCloudBootsAttribute(Consumer<Component> tooltipAdder, TooltipDisplay tooltipDisplay, Player player, CallbackInfo ci) {
        if(((ItemStack)(Object)this).getItem() instanceof CloudBootsItem cloudBootsItem) {
            ItemAttributeModifiers.Display.attributeModifiers().apply(tooltipAdder, player, Attributes.MOVEMENT_SPEED, new AttributeModifier(CloudBootsItem.SPEED_MODIFIER, cloudBootsItem.getSpeedModifier(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }
}