package com.tiviacz.cloudboots.item;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.tiviacz.cloudboots.config.CloudBootsConfig;
import com.tiviacz.cloudboots.init.ModItems;
import com.tiviacz.cloudboots.item.armor.DefaultArmorMaterial;
import com.tiviacz.cloudboots.platform.Platform;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class CloudBootsItem extends ArmorItem {
    private static final EnumMap<Type, UUID> ARMOR_MODIFIER_UUID_PER_TYPE = (EnumMap)Util.make(new EnumMap(Type.class), (enumMap) -> {
        enumMap.put(ArmorItem.Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
        enumMap.put(ArmorItem.Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
        enumMap.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
        enumMap.put(ArmorItem.Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
    });
    private final Supplier<Multimap<Attribute, AttributeModifier>> attributeModifier;
    private final Supplier<Integer> jumpBoostLevel;
    private final Supplier<Double> speedModifier;
    private final Supplier<Double> flyingSpeedModifier;
    private final Supplier<Boolean> negatesFallDamage;

    public CloudBootsItem(DefaultArmorMaterial armorMaterial, Properties properties) {
        super(armorMaterial, Type.BOOTS, properties.stacksTo(1));
        CloudBootsConfig.Server.TierConfig config = CloudBootsConfig.getProperConfig(armorMaterial);
        this.jumpBoostLevel = config.jumpBoostLevel;
        this.speedModifier = config.speedModifier;
        this.flyingSpeedModifier = config.flyingSpeedModifier;
        this.negatesFallDamage = config.negatesFallDamage;
        this.attributeModifier = Suppliers.memoize(() -> {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            UUID uUID = ARMOR_MODIFIER_UUID_PER_TYPE.get(type);
            builder.put(Attributes.ARMOR, new AttributeModifier(uUID, "Armor modifier", this.getDefense(), AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uUID, "Armor toughness", this.getToughness(), AttributeModifier.Operation.ADDITION));
            if(material == ArmorMaterials.NETHERITE) {
                builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uUID, "Armor knockback resistance", this.knockbackResistance, AttributeModifier.Operation.ADDITION));
            }
            builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uUID, "Armor speed", speedModifier.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            return builder.build();
        });
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
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.FEET ? this.attributeModifier.get() : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entityIn, int itemSlot, boolean isSelected) {
        if(entityIn instanceof Player player) {
            if(player.getItemBySlot(EquipmentSlot.FEET).getItem() == this) {
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 0, getJumpBoostLevel(), false, false));
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);

        if(stack.getItem() == Platform.getItem(ModItems.CLOUD_BOOTS_ID)) {
            tooltipComponents.add(Component.translatable("item.cloudboots.og_boots").withStyle(ChatFormatting.BLUE));
        }

        if(negatesFallDamage()) {
            tooltipComponents.add(Component.translatable("item.cloudboots.negates_fall_damage").withStyle(ChatFormatting.BLUE));
        }
        MutableComponent mutablecomponent = Component.translatable(MobEffects.JUMP.getDescriptionId());
        mutablecomponent = Component.translatable("potion.withAmplifier", mutablecomponent, Component.translatable("potion.potency." + getJumpBoostLevel())).withStyle(ChatFormatting.BLUE);
        tooltipComponents.add(mutablecomponent);
    }

    //Custom tick method to spawn particles
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