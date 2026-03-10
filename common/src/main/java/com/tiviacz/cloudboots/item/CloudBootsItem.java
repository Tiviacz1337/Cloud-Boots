package com.tiviacz.cloudboots.item;

import com.google.common.base.Suppliers;
import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.config.CloudBootsConfig;
import com.tiviacz.cloudboots.init.ModArmorMaterials;
import com.tiviacz.cloudboots.init.ModItems;
import com.tiviacz.cloudboots.platform.Platform;
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
    private final Supplier<Integer> jumpBoostLevel;
    private final Supplier<Double> speedModifier;
    private final Supplier<Double> flyingSpeedModifier;
    private final Supplier<Boolean> negatesFallDamage;

    public CloudBootsItem(Holder<ArmorMaterial> material, Properties properties) {
        super(material, Type.BOOTS, properties.stacksTo(1).durability(ArmorItem.Type.BOOTS.getDurability(getDurability(material))));
        CloudBootsConfig.Server.TierConfig config = CloudBootsConfig.getProperConfig(material);
        this.jumpBoostLevel = config.jumpBoostLevel;
        this.speedModifier = config.speedModifier;
        this.flyingSpeedModifier = config.flyingSpeedModifier;
        this.negatesFallDamage = config.negatesFallDamage;

        this.defaultModifiers = Suppliers.memoize(() -> {
            int i = material.value().getDefense(type);
            float f = material.value().toughness();
            ItemAttributeModifiers.Builder itemattributemodifiers$builder = ItemAttributeModifiers.builder();
            EquipmentSlotGroup equipmentslotgroup = EquipmentSlotGroup.bySlot(type.getSlot());
            ResourceLocation resourcelocation = ResourceLocation.withDefaultNamespace("armor." + type.getName());
            itemattributemodifiers$builder.add(Attributes.ARMOR, new AttributeModifier(resourcelocation, i, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup);
            itemattributemodifiers$builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(resourcelocation, f, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup);
            float f1 = material.value().knockbackResistance();
            if(f1 > 0.0F) {
                itemattributemodifiers$builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(resourcelocation, f1, AttributeModifier.Operation.ADD_VALUE), equipmentslotgroup);
            }
            itemattributemodifiers$builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "armor.speed"), speedModifier.get(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), equipmentslotgroup);
            return itemattributemodifiers$builder.build();
        });
    }

    public static int getDurability(Holder<ArmorMaterial> material) {
        if(material.is(ModArmorMaterials.IRON_CLOUD)) {
            return 15;
        }
        if(material.is(ModArmorMaterials.GOLD_CLOUD)) {
            return 7;
        }
        if(material.is(ModArmorMaterials.DIAMOND_CLOUD)) {
            return 33;
        }
        if(material.is(ModArmorMaterials.NETHERITE_CLOUD)) {
            return 37;
        }
        return 33;
    }

    public int getJumpBoostLevel() {
        return this.jumpBoostLevel.get();
    }

    public double getSpeedModifier() {
        return this.speedModifier.get();
    }

    public double getFlyingSpeedModifier() {
        return this.flyingSpeedModifier.get();
    }

    public boolean negatesFallDamage() {
        return this.negatesFallDamage.get();
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        return this.defaultModifiers.get();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entityIn, int itemSlot, boolean isSelected) {
        if(entityIn instanceof Player player) {
            if(player.getItemBySlot(EquipmentSlot.FEET).getItem() == this) {
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 0, getJumpBoostLevel(), false, false));
                if(!player.onGround()) {
                    if(player.fallDistance >= 1.0F) {
                        spawnParticles(level, player);
                        if(negatesFallDamage.get()) {
                            player.fallDistance = 0F;
                        }
                    }
                }
                if(player.isSprinting()) {
                    spawnParticles(level, player);
                }
            }
        }
    }

    public void spawnParticles(Level level, Player player) {
        if(CloudBootsConfig.SERVER.spawnParticles.get()) {
            if(!level.isClientSide && level instanceof ServerLevel server && level.random.nextFloat() > 0.5F) {
                server.sendParticles(ParticleTypes.CLOUD, player.xo, player.yo, player.zo, 1, 0, 0, 0, (level.random.nextFloat() - 0.5F));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        if(stack.getItem() == Platform.getItem(ModItems.CLOUD_BOOTS_ID)) {
            tooltipComponents.add(Component.translatable("item.cloudboots.og_boots").withStyle(ChatFormatting.BLUE));
        }

        if(negatesFallDamage()) {
            tooltipComponents.add(Component.translatable("item.cloudboots.negates_fall_damage").withStyle(ChatFormatting.BLUE));
        }
        MutableComponent mutablecomponent = Component.translatable(MobEffects.JUMP.value().getDescriptionId());
        mutablecomponent = Component.translatable("potion.withAmplifier", mutablecomponent, Component.translatable("potion.potency." + getJumpBoostLevel())).withStyle(ChatFormatting.BLUE);
        tooltipComponents.add(mutablecomponent);
    }
}