package com.tiviacz.cloudboots.item;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.config.CloudBootsConfig;
import com.tiviacz.cloudboots.init.ModItems;
import com.tiviacz.cloudboots.platform.Platform;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CloudBootsItem extends Item {
    public static final ResourceLocation SPEED_MODIFIER = ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "armor.speed");
    private final Supplier<Integer> jumpBoostLevel;
    private final Supplier<Double> speedModifier;
    private final Supplier<Double> flyingSpeedModifier;
    private final Supplier<Boolean> negatesFallDamage;

    public CloudBootsItem(Properties properties, ArmorMaterial armorMaterial, ArmorType type) {
        super(properties.stacksTo(1).humanoidArmor(armorMaterial, type));
        CloudBootsConfig.Server.TierConfig config = CloudBootsConfig.getProperConfig(armorMaterial);
        this.jumpBoostLevel = config.jumpBoostLevel;
        this.speedModifier = config.speedModifier;
        this.flyingSpeedModifier = config.flyingSpeedModifier;
        this.negatesFallDamage = config.negatesFallDamage;
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
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
        if(entity instanceof Player player) {
            if(player.getItemBySlot(EquipmentSlot.FEET).getItem() == this) {
                player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 0, getJumpBoostLevel(), false, false));
                if(!player.onGround()) {
                    if(player.fallDistance >= 1.0F) {
                        spawnParticles(level, player);
                    }
                }
                if(player.isSprinting()) {
                    spawnParticles(level, player);
                }
            }
        }
        super.inventoryTick(stack, level, entity, slot);
    }

    public void spawnParticles(Level level, Player player) {
        if(CloudBootsConfig.SERVER.spawnParticles.get()) {
            if(!level.isClientSide && level instanceof ServerLevel server && level.random.nextFloat() > 0.5F) {
                server.sendParticles(ParticleTypes.CLOUD, player.xo, player.yo, player.zo, 1, 0, 0, 0, (level.random.nextFloat() - 0.5F));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);

        if(stack.getItem() == Platform.getItem(ModItems.CLOUD_BOOTS_ID)) {
            tooltipAdder.accept(Component.translatable("item.cloudboots.og_boots").withStyle(ChatFormatting.BLUE));
        }

        if(negatesFallDamage()) {
            tooltipAdder.accept(Component.translatable("item.cloudboots.negates_fall_damage").withStyle(ChatFormatting.BLUE));
        }
        MutableComponent mutablecomponent = Component.translatable(MobEffects.JUMP_BOOST.value().getDescriptionId());
        mutablecomponent = Component.translatable("potion.withAmplifier", mutablecomponent, Component.translatable("potion.potency." + getJumpBoostLevel())).withStyle(ChatFormatting.BLUE);
        tooltipAdder.accept(mutablecomponent);
    }

    //Custom tick method to apply speed attribute modifier read from config
    public static void tick(Player player) {
        if(player.level().isClientSide()) {
            return;
        }
        AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if(speedAttribute == null) return;

        boolean wearingBoots = player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof CloudBootsItem;
        boolean hasModifier = speedAttribute.hasModifier(SPEED_MODIFIER);

        if(wearingBoots) {
            if(!hasModifier) {
                CloudBootsItem cloudBoots = (CloudBootsItem)player.getItemBySlot(EquipmentSlot.FEET).getItem();
                AttributeModifier modifier = new AttributeModifier(SPEED_MODIFIER, cloudBoots.getSpeedModifier(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
                speedAttribute.addTransientModifier(modifier);
            }
        } else {
            if(hasModifier) {
                speedAttribute.removeModifier(SPEED_MODIFIER);
            }
        }
    }

    public static boolean negateFallDamage(LivingEntity livingEntity, DamageSource damageSource) {
        if(damageSource.is(DamageTypeTags.IS_FALL)) {
            if(livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof CloudBootsItem cloudBootsItem) {
                if(cloudBootsItem.negatesFallDamage()) {
                    return true;
                }
            }
            if(livingEntity.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof GoldenFeatherItem || livingEntity.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof GoldenFeatherItem || Platform.isGoldenFeatherEquipped(livingEntity)) {
                return true;
            }
        }
        return false;
    }
}