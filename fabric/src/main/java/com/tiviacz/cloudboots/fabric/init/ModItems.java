package com.tiviacz.cloudboots.fabric.init;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.item.CloudBootsItem;
import com.tiviacz.cloudboots.item.GoldenFeatherItem;
import com.tiviacz.cloudboots.item.armor.DefaultArmorMaterial;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;

public class ModItems {
    public static Item CLOUD_BOOTS;
    public static Item IRON_CLOUD_BOOTS;
    public static Item GOLD_CLOUD_BOOTS;
    public static Item DIAMOND_CLOUD_BOOTS;
    public static Item NETHERITE_CLOUD_BOOTS;
    public static Item GOLDEN_FEATHER;

    public static void register() {
        CLOUD_BOOTS = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(CloudBoots.MODID, "cloud_boots"), new CloudBootsItem(new DefaultArmorMaterial(ArmorMaterials.DIAMOND, "cloud"), new Item.Properties()));
        IRON_CLOUD_BOOTS = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(CloudBoots.MODID, "iron_cloud_boots"), new CloudBootsItem(new DefaultArmorMaterial(ArmorMaterials.IRON, "iron"), new Item.Properties()));
        GOLD_CLOUD_BOOTS = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(CloudBoots.MODID, "gold_cloud_boots"), new CloudBootsItem(new DefaultArmorMaterial(ArmorMaterials.GOLD, "gold"), new Item.Properties()));
        DIAMOND_CLOUD_BOOTS = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(CloudBoots.MODID, "diamond_cloud_boots"), new CloudBootsItem(new DefaultArmorMaterial(ArmorMaterials.DIAMOND, "diamond"), new Item.Properties()));
        NETHERITE_CLOUD_BOOTS = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(CloudBoots.MODID, "netherite_cloud_boots"), new CloudBootsItem(new DefaultArmorMaterial(ArmorMaterials.NETHERITE, "netherite"), new Item.Properties()));
        GOLDEN_FEATHER = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(CloudBoots.MODID, "golden_feather"), new GoldenFeatherItem(new Item.Properties()));
    }
}