package com.tiviacz.cloudboots.neoforge.init;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.init.ModArmorMaterials;
import com.tiviacz.cloudboots.item.CloudBootsItem;
import com.tiviacz.cloudboots.item.GoldenFeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CloudBoots.MODID);

    public static final DeferredItem<Item> CLOUD_BOOTS = ITEMS.registerItem("cloud_boots", (properties) -> new CloudBootsItem(properties, ModArmorMaterials.CLOUD, ArmorType.BOOTS));
    public static final DeferredItem<Item> IRON_CLOUD_BOOTS = ITEMS.registerItem("iron_cloud_boots", (properties) -> new CloudBootsItem(properties, ModArmorMaterials.IRON, ArmorType.BOOTS));
    public static final DeferredItem<Item> GOLD_CLOUD_BOOTS = ITEMS.registerItem("gold_cloud_boots", (properties) -> new CloudBootsItem(properties, ModArmorMaterials.GOLD, ArmorType.BOOTS));
    public static final DeferredItem<Item> DIAMOND_CLOUD_BOOTS = ITEMS.registerItem("diamond_cloud_boots", (properties) -> new CloudBootsItem(properties, ModArmorMaterials.DIAMOND, ArmorType.BOOTS));
    public static final DeferredItem<Item> NETHERITE_CLOUD_BOOTS = ITEMS.registerItem("netherite_cloud_boots", (properties) -> new CloudBootsItem(properties, ModArmorMaterials.NETHERITE, ArmorType.BOOTS));
    public static final DeferredItem<Item> GOLDEN_FEATHER = ITEMS.registerItem("golden_feather", GoldenFeatherItem::new);
}