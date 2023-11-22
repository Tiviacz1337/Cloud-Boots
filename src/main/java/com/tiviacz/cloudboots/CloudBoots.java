package com.tiviacz.cloudboots;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CloudBoots implements ModInitializer
{
    public static final String MODID = "cloudboots";
    public static boolean trinketsLoaded = false;

    @Override
    public void onInitialize()
    {
        ModItems.init();

        trinketsLoaded = FabricLoader.getInstance().isModLoaded("trinkets");
        if(trinketsLoaded) TrinketsCompat.init();
    }

    public static class ModItems
    {
        public static Item CLOUD_BOOTS;
        public static Item IRON_CLOUD_BOOTS;
        public static Item GOLD_CLOUD_BOOTS;
        public static Item DIAMOND_CLOUD_BOOTS;
        public static Item NETHERITE_CLOUD_BOOTS;
        public static Item GOLDEN_FEATHER;

        public static void init()
        {
            CLOUD_BOOTS = Registry.register(Registry.ITEM, new Identifier(CloudBoots.MODID, "cloud_boots"), new CloudBootsItem(CloudBootsItem.CloudArmorMaterial.CLOUD, 0.15D, 4));
            IRON_CLOUD_BOOTS = Registry.register(Registry.ITEM, new Identifier(CloudBoots.MODID, "iron_cloud_boots"), new CloudBootsItem(ArmorMaterials.IRON, 0.05D, 1));
            GOLD_CLOUD_BOOTS = Registry.register(Registry.ITEM, new Identifier(CloudBoots.MODID, "gold_cloud_boots"), new CloudBootsItem(ArmorMaterials.GOLD, 0.10D, 2));
            DIAMOND_CLOUD_BOOTS = Registry.register(Registry.ITEM, new Identifier(CloudBoots.MODID, "diamond_cloud_boots"), new CloudBootsItem(ArmorMaterials.DIAMOND, 0.15D, 3));
            NETHERITE_CLOUD_BOOTS = Registry.register(Registry.ITEM, new Identifier(CloudBoots.MODID, "netherite_cloud_boots"), new CloudBootsItem(ArmorMaterials.NETHERITE, 0.20D, 4));
            GOLDEN_FEATHER = Registry.register(Registry.ITEM, new Identifier(CloudBoots.MODID, "golden_feather"), new GoldenFeatherItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1).maxDamageIfAbsent(385)));
        }
    }
}