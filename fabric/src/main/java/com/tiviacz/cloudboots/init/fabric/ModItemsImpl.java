package com.tiviacz.cloudboots.init.fabric;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.item.CloudBootsItem;
import com.tiviacz.cloudboots.item.GoldenFeatherItem;
import com.tiviacz.cloudboots.init.ModArmorMaterials;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ModItemsImpl {
    public static Item CLOUD_BOOTS;
    public static Item IRON_CLOUD_BOOTS;
    public static Item GOLD_CLOUD_BOOTS;
    public static Item DIAMOND_CLOUD_BOOTS;
    public static Item NETHERITE_CLOUD_BOOTS;
    public static Item GOLDEN_FEATHER;

    public static void register() {
        CLOUD_BOOTS = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "cloud_boots"), new CloudBootsItem(ModArmorMaterials.CLOUD, 0.15D, 4));
        IRON_CLOUD_BOOTS = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "iron_cloud_boots"), new CloudBootsItem(ModArmorMaterials.IRON_CLOUD, 0.05D, 1));
        GOLD_CLOUD_BOOTS = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "gold_cloud_boots"), new CloudBootsItem(ModArmorMaterials.GOLD_CLOUD, 0.10D, 2));
        DIAMOND_CLOUD_BOOTS = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "diamond_cloud_boots"), new CloudBootsItem(ModArmorMaterials.DIAMOND_CLOUD, 0.15D, 3));
        NETHERITE_CLOUD_BOOTS = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "netherite_cloud_boots"), new CloudBootsItem(ModArmorMaterials.NETHERITE_CLOUD, 0.20D, 4));
        GOLDEN_FEATHER = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "golden_feather"), new GoldenFeatherItem(new Item.Properties().stacksTo(1).durability(385)));
    }

    public static Supplier<Item> getItem(ResourceLocation id) {
        return () -> BuiltInRegistries.ITEM.get(id);
    }
}
