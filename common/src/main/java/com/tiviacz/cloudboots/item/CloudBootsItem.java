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
import net.minecraft.util.RandomSource;
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
            }
        }
        super.inventoryTick(stack, level, entity, slot);
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

    //Custom tick method to apply speed attribute modifier read from config and spawn particles
    public static void tick(Player player) {
        CloudBootsItem cloudBoots = areCloudBootsEquipped(player);

        boolean bootsEquipped = cloudBoots != null;
        boolean featherEquipped = isGoldenFeatherEquipped(player);

        if(bootsEquipped || featherEquipped) {
            if(!player.onGround()) {
                if(player.fallDistance >= (bootsEquipped ? 1.0F : 3.0F)) {
                    spawnClientParticles(player, 1, 0.5F);
                }
            }
            if(player.isSprinting() && bootsEquipped) {
                spawnClientParticles(player, 1, 0.5F);
            }
        }

        if(player.level().isClientSide()) {
            return;
        }

        AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if(speedAttribute == null) return;
        boolean hasModifier = speedAttribute.hasModifier(SPEED_MODIFIER);

        if(bootsEquipped) {
            if(!hasModifier) {
                AttributeModifier modifier = new AttributeModifier(SPEED_MODIFIER, cloudBoots.getSpeedModifier(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
                speedAttribute.addTransientModifier(modifier);
            }
        } else {
            if(hasModifier) {
                speedAttribute.removeModifier(SPEED_MODIFIER);
            }
        }
    }

    public static void spawnClientParticles(Player player, int count, float chance) {
        if(!CloudBootsConfig.clientSpec.isLoaded()) return;
        if(!CloudBootsConfig.CLIENT.spawnParticles.get()) return;

        if(player.level().random.nextFloat() < chance) {
            return;
        }
        for(int i = 0; i < count; ++i) {
            RandomSource random = player.level().getRandom();
            double maxSpeed = 0 + random.nextFloat() * (0.2 - 0.0);
            double g = random.nextGaussian() * 0;
            double h = random.nextGaussian() * 0;
            double j = random.nextGaussian() * 0;
            double k = random.nextGaussian() * maxSpeed;
            double l = random.nextGaussian() * maxSpeed;
            double m = random.nextGaussian() * maxSpeed;
            player.level().addParticle(ParticleTypes.POOF, player.getX() + g, player.getY() + h, player.getZ() + j, k, l, m);
        }
    }

    public static boolean isCloudGearEquipped(LivingEntity livingEntity) {
        if(areCloudBootsEquipped(livingEntity) != null) {
            return true;
        }
        return isGoldenFeatherEquipped(livingEntity);
    }

    public static boolean negateFallDamage(LivingEntity livingEntity, DamageSource damageSource) {
        if(damageSource.is(DamageTypeTags.IS_FALL)) {
            CloudBootsItem cloudBootsItem = areCloudBootsEquipped(livingEntity);
            if(cloudBootsItem != null && cloudBootsItem.negatesFallDamage()) {
                return true;
            }
            return isGoldenFeatherEquipped(livingEntity);
        }
        return false;
    }

    public static CloudBootsItem areCloudBootsEquipped(LivingEntity livingEntity) {
        if(livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof CloudBootsItem cloudBootsItem) {
            return cloudBootsItem;
        }
        return null;
    }

    public static boolean isGoldenFeatherEquipped(LivingEntity livingEntity) {
        return livingEntity.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof GoldenFeatherItem || livingEntity.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof GoldenFeatherItem || Platform.isGoldenFeatherEquipped(livingEntity);
    }
}