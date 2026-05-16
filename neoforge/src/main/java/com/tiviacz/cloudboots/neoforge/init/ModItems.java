package com.tiviacz.cloudboots.neoforge.init;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.item.CloudBootsItem;
import com.tiviacz.cloudboots.item.GoldenFeatherItem;
import com.tiviacz.cloudboots.init.ModArmorMaterials;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CloudBoots.MODID);

    public static final DeferredItem<Item> CLOUD_BOOTS = ITEMS.register("cloud_boots", () -> new CloudBootsItem(ModArmorMaterials.CLOUD, new Item.Properties()));
    public static final DeferredItem<Item> IRON_CLOUD_BOOTS = ITEMS.register("iron_cloud_boots", () -> new CloudBootsItem(ModArmorMaterials.IRON_CLOUD, new Item.Properties()));
    public static final DeferredItem<Item> GOLD_CLOUD_BOOTS = ITEMS.register("gold_cloud_boots", () -> new CloudBootsItem(ModArmorMaterials.GOLD_CLOUD, new Item.Properties()));
    public static final DeferredItem<Item> DIAMOND_CLOUD_BOOTS = ITEMS.register("diamond_cloud_boots", () -> new CloudBootsItem(ModArmorMaterials.DIAMOND_CLOUD, new Item.Properties()));
    public static final DeferredItem<Item> NETHERITE_CLOUD_BOOTS = ITEMS.register("netherite_cloud_boots", () -> new CloudBootsItem(ModArmorMaterials.NETHERITE_CLOUD, new Item.Properties().fireResistant()));
    public static final DeferredItem<Item> GOLDEN_FEATHER = ITEMS.register("golden_feather", () -> new GoldenFeatherItem(new Item.Properties()));
}