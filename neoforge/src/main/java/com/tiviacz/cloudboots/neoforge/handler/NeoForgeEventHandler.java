package com.tiviacz.cloudboots.neoforge.handler;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.item.CloudBootsItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddAttributeTooltipsEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = CloudBoots.MODID)
public class NeoForgeEventHandler {
    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Post event) {
        CloudBootsItem.tick(event.getEntity());
    }

    @SubscribeEvent
    public static void addSpeedModifierTooltip(AddAttributeTooltipsEvent event) {
        if(event.getStack().getItem() instanceof CloudBootsItem cloudBootsItem) {
            Component component = addModifierTooltip(event.getContext().player(), Attributes.MOVEMENT_SPEED, new AttributeModifier(CloudBootsItem.SPEED_MODIFIER, cloudBootsItem.getSpeedModifier(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            if(component != null) {
                event.addTooltipLines(component);
            }
        }
    }

    private static Component addModifierTooltip(@Nullable Player player, Holder<Attribute> attribute, AttributeModifier modifier) {
        double d = modifier.amount();
        boolean bl = false;
        if(player != null) {
            if(modifier.is(Item.BASE_ATTACK_DAMAGE_ID)) {
                d += player.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
                bl = true;
            } else if(modifier.is(Item.BASE_ATTACK_SPEED_ID)) {
                d += player.getAttributeBaseValue(Attributes.ATTACK_SPEED);
                bl = true;
            }
        }

        double e;
        if(modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE || modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
            e = d * 100.0;
        } else if(attribute.is(Attributes.KNOCKBACK_RESISTANCE)) {
            e = d * 10.0;
        } else {
            e = d;
        }

        if(bl) {
            return CommonComponents.space().append(Component.translatable("attribute.modifier.equals." + modifier.operation().id(), ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(e), Component.translatable(attribute.value().getDescriptionId()))).withStyle(ChatFormatting.DARK_GREEN);
        } else if(d > 0.0) {
            return Component.translatable("attribute.modifier.plus." + modifier.operation().id(), ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(e), Component.translatable(attribute.value().getDescriptionId())).withStyle(attribute.value().getStyle(true));
        } else if(d < 0.0) {
            return Component.translatable("attribute.modifier.take." + modifier.operation().id(), ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(-e), Component.translatable(attribute.value().getDescriptionId())).withStyle(attribute.value().getStyle(false));
        }
        return null;
    }
}