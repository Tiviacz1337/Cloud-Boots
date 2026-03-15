package com.tiviacz.cloudboots.init;

import com.google.common.collect.Maps;
import com.tiviacz.cloudboots.CloudBoots;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

public class ModArmorMaterials {
    public static ResourceKey<EquipmentAsset> CLOUD_ID = createId("cloud");
    public static ResourceKey<EquipmentAsset> IRON_CLOUD_ID = createId("iron_cloud");
    public static ResourceKey<EquipmentAsset> GOLD_CLOUD_ID = createId("gold_cloud");
    public static ResourceKey<EquipmentAsset> DIAMOND_CLOUD_ID = createId("diamond_cloud");
    public static ResourceKey<EquipmentAsset> NETHERITE_CLOUD_ID = createId("netherite_cloud");

    public static final ArmorMaterial CLOUD = new ArmorMaterial(33, makeDefense(3, 6, 8, 3, 11), 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, ModTags.REPAIRS_CLOUD_ARMOR, CLOUD_ID);
    public static final ArmorMaterial IRON = new ArmorMaterial(15, makeDefense(2, 5, 6, 2, 5), 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, ItemTags.REPAIRS_IRON_ARMOR, IRON_CLOUD_ID);
    public static final ArmorMaterial GOLD = new ArmorMaterial(7, makeDefense(1, 3, 5, 2, 7), 25, SoundEvents.ARMOR_EQUIP_GOLD, 0.0F, 0.0F, ItemTags.REPAIRS_GOLD_ARMOR, GOLD_CLOUD_ID);
    public static final ArmorMaterial DIAMOND = new ArmorMaterial(33, makeDefense(3, 6, 8, 3, 11), 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, ItemTags.REPAIRS_DIAMOND_ARMOR, DIAMOND_CLOUD_ID);
    public static final ArmorMaterial NETHERITE = new ArmorMaterial(37, makeDefense(3, 6, 8, 3, 11), 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F, ItemTags.REPAIRS_NETHERITE_ARMOR, NETHERITE_CLOUD_ID);

    private static Map<ArmorType, Integer> makeDefense(int boots, int leggings, int chestplate, int helmet, int body) {
        return Maps.newEnumMap(Map.of(ArmorType.BOOTS, boots, ArmorType.LEGGINGS, leggings, ArmorType.CHESTPLATE, chestplate, ArmorType.HELMET, helmet, ArmorType.BODY, body));
    }

    public static ResourceKey<EquipmentAsset> createId(String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(CloudBoots.MODID, name));
    }
}