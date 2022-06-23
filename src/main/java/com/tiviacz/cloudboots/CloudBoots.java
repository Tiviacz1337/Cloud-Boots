package com.tiviacz.cloudboots;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CloudBoots implements ModInitializer
{
    public static final String MODID = "cloudboots";

    @Override
    public void onInitialize()
    {
        ModItems.init();
    }

    public static class ModItems
    {
        public static Item CLOUD_BOOTS;
        public static Item GOLDEN_FEATHER;

        public static void init()
        {
            CLOUD_BOOTS = Registry.register(Registry.ITEM, new Identifier(CloudBoots.MODID, "cloud_boots"), new CloudBootsItem(new Item.Settings().group(ItemGroup.COMBAT)));
            GOLDEN_FEATHER = Registry.register(Registry.ITEM, new Identifier(CloudBoots.MODID, "golden_feather"), new GoldenFeatherItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1).maxDamageIfAbsent(385)));
        }
    }
}