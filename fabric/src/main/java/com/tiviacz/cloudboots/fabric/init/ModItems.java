package com.tiviacz.cloudboots.fabric.init;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.init.ModArmorMaterials;
import com.tiviacz.cloudboots.item.CloudBootsItem;
import com.tiviacz.cloudboots.item.GoldenFeatherItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;

public class ModItems {
    public static Item CLOUD_BOOTS;
    public static Item IRON_CLOUD_BOOTS;
    public static Item GOLD_CLOUD_BOOTS;
    public static Item DIAMOND_CLOUD_BOOTS;
    public static Item NETHERITE_CLOUD_BOOTS;
    public static Item GOLDEN_FEATHER;

    public static void register() {
        CLOUD_BOOTS = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "cloud_boots"), new CloudBootsItem(new Item.Properties().setId(resourceKey("cloud_boots")), ModArmorMaterials.CLOUD, ArmorType.BOOTS));
        IRON_CLOUD_BOOTS = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "iron_cloud_boots"), new CloudBootsItem(new Item.Properties().setId(resourceKey("iron_cloud_boots")), ModArmorMaterials.IRON, ArmorType.BOOTS));
        GOLD_CLOUD_BOOTS = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "gold_cloud_boots"), new CloudBootsItem(new Item.Properties().setId(resourceKey("gold_cloud_boots")), ModArmorMaterials.GOLD, ArmorType.BOOTS));
        DIAMOND_CLOUD_BOOTS = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "diamond_cloud_boots"), new CloudBootsItem(new Item.Properties().setId(resourceKey("diamond_cloud_boots")), ModArmorMaterials.DIAMOND, ArmorType.BOOTS));
        NETHERITE_CLOUD_BOOTS = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "netherite_cloud_boots"), new CloudBootsItem(new Item.Properties().setId(resourceKey("netherite_cloud_boots")), ModArmorMaterials.NETHERITE, ArmorType.BOOTS));
        GOLDEN_FEATHER = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "golden_feather"), new GoldenFeatherItem(new Item.Properties().setId(resourceKey("golden_feather")).stacksTo(1).durability(385)));
    }

    public static ResourceLocation resourceLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, name);
    }

    public static ResourceKey<Item> resourceKey(String name) {
        return ResourceKey.create(Registries.ITEM, resourceLocation(name));
    }
}