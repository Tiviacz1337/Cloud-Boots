package com.tiviacz.cloudboots.init;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.platform.Platform;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final Holder<ArmorMaterial> CLOUD = register("cloud",
            Util.make(new EnumMap<>(ArmorItem.Type.class), attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 5);
                attribute.put(ArmorItem.Type.LEGGINGS, 7);
                attribute.put(ArmorItem.Type.CHESTPLATE, 9);
                attribute.put(ArmorItem.Type.HELMET, 5);
                attribute.put(ArmorItem.Type.BODY, 11);
            }), 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0.0f, () -> Ingredient.of(Items.GOLD_INGOT, Platform.getItem(ModItems.GOLDEN_FEATHER_ID).get()));

    public static final Holder<ArmorMaterial> IRON_CLOUD = register("iron_cloud",
            Util.make(new EnumMap<>(ArmorItem.Type.class), attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 5);
                attribute.put(ArmorItem.Type.CHESTPLATE, 6);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 5);
            }), 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> Ingredient.of(Items.IRON_INGOT));

    public static final Holder<ArmorMaterial> GOLD_CLOUD = register("gold_cloud",
            Util.make(new EnumMap<>(ArmorItem.Type.class), attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 1);
                attribute.put(ArmorItem.Type.LEGGINGS, 3);
                attribute.put(ArmorItem.Type.CHESTPLATE, 5);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 7);
            }), 25, SoundEvents.ARMOR_EQUIP_GOLD, 0.0F, 0.0F, () -> Ingredient.of(Items.GOLD_INGOT));

    public static final Holder<ArmorMaterial> DIAMOND_CLOUD = register("diamond_cloud", Util.make(new EnumMap<>(ArmorItem.Type.class), p_323380_ -> {
        p_323380_.put(ArmorItem.Type.BOOTS, 3);
        p_323380_.put(ArmorItem.Type.LEGGINGS, 6);
        p_323380_.put(ArmorItem.Type.CHESTPLATE, 8);
        p_323380_.put(ArmorItem.Type.HELMET, 3);
        p_323380_.put(ArmorItem.Type.BODY, 11);
    }), 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, () -> Ingredient.of(Items.DIAMOND));

    public static final Holder<ArmorMaterial> NETHERITE_CLOUD = register("netherite_cloud", Util.make(new EnumMap<>(ArmorItem.Type.class), p_323379_ -> {
        p_323379_.put(ArmorItem.Type.BOOTS, 3);
        p_323379_.put(ArmorItem.Type.LEGGINGS, 6);
        p_323379_.put(ArmorItem.Type.CHESTPLATE, 8);
        p_323379_.put(ArmorItem.Type.HELMET, 3);
        p_323379_.put(ArmorItem.Type.BODY, 11);
    }), 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F, () -> Ingredient.of(Items.NETHERITE_INGOT));

    private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> typeProtection,
                                                  int enchantability, Holder<SoundEvent> sound, float toughness, float knockbackResistance,
                                                  Supplier<Ingredient> ingredientItem) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, name);
        Holder<SoundEvent> equipSound = sound;
        Supplier<Ingredient> ingredient = ingredientItem;
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(location));

        EnumMap<ArmorItem.Type, Integer> typeMap = new EnumMap<>(ArmorItem.Type.class);
        for(ArmorItem.Type type : ArmorItem.Type.values()) {
            typeMap.put(type, typeProtection.get(type));
        }

        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, location,
                new ArmorMaterial(typeProtection, enchantability, equipSound, ingredient, layers, toughness, knockbackResistance));
    }
}