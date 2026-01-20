package com.tiviacz.cloudboots.item;

import com.google.common.base.Suppliers;
import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Supplier;

public class CloudBootsItem extends ArmorItem {
    private final Supplier<ItemAttributeModifiers> defaultModifiers;
    private final int jumpBoostLevel;

    public CloudBootsItem(Holder<ArmorMaterial> material, double speedModifier, int jumpBoostLevel, Properties properties) {
        super(material, Type.BOOTS, properties.stacksTo(1));
        this.jumpBoostLevel = jumpBoostLevel;
        this.defaultModifiers = Suppliers.memoize(
                () -> {
                    int i = material.value().getDefense(type);
                    float f = material.value().toughness();
                    ItemAttributeModifiers.Builder itemattributemodifiers$builder = ItemAttributeModifiers.builder();
                    EquipmentSlotGroup equipmentslotgroup = EquipmentSlotGroup.bySlot(type.getSlot());
                    ResourceLocation resourcelocation = ResourceLocation.withDefaultNamespace("armor." + type.getName());
                    itemattributemodifiers$builder.add(
                            Attributes.ARMOR, new AttributeModifier(resourcelocation, (double)i, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup
                    );
                    itemattributemodifiers$builder.add(
                            Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resourcelocation, (double)f, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup
                    );
                    float f1 = material.value().knockbackResistance();
                    if(f1 > 0.0F) {
                        itemattributemodifiers$builder.add(
                                Attributes.KNOCKBACK_RESISTANCE,
                                new AttributeModifier(resourcelocation, (double)f1, AttributeModifier.Operation.ADD_VALUE),
                                equipmentslotgroup
                        );
                    }
                    itemattributemodifiers$builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "armor.speed"), speedModifier, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), equipmentslotgroup);

                    return itemattributemodifiers$builder.build();
                }
        );
    }

    public int getJumpBoostLevel() {
        return this.jumpBoostLevel;
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        return this.defaultModifiers.get();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entityIn, int itemSlot, boolean isSelected) {
        if(entityIn instanceof Player player) {
            if(player.getItemBySlot(EquipmentSlot.FEET).getItem() == this) {
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 0, this.jumpBoostLevel, false, false));

                if(!player.onGround()) {
                    if(player.fallDistance >= 1.0F) {
                        if(!level.isClientSide && level instanceof ServerLevel server) {
                            server.sendParticles(ParticleTypes.CLOUD, player.xo, player.yo, player.zo, 3, 0, 0, 0, (level.random.nextFloat() - 0.5F));
                        }
                        player.fallDistance = 0F;
                    }
                }

                if(player.isSprinting()) {
                    if(!level.isClientSide && level instanceof ServerLevel server) {
                        server.sendParticles(ParticleTypes.CLOUD, player.xo, player.yo, player.zo, 1, 0, 0, 0, (level.random.nextFloat() - 0.5F));
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        if(stack.getItem() == ModItems.getItem(ModItems.CLOUD_BOOTS)) {
            tooltipComponents.add(Component.translatable("item.cloudboots.og_boots").withStyle(ChatFormatting.BLUE));
        }

        tooltipComponents.add(Component.translatable("item.cloudboots.negates_fall_damage").withStyle(ChatFormatting.BLUE));
        MutableComponent mutablecomponent = Component.translatable(MobEffects.JUMP.value().getDescriptionId());
        mutablecomponent = Component.translatable("potion.withAmplifier", mutablecomponent, Component.translatable("potion.potency." + this.jumpBoostLevel)).withStyle(ChatFormatting.BLUE);
        tooltipComponents.add(mutablecomponent);
    }
}