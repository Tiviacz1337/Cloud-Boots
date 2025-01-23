package com.tiviacz.cloudboots.init.neoforge;

import com.tiviacz.cloudboots.item.CloudBootsItem;
import com.tiviacz.cloudboots.item.GoldenFeatherItem;
import com.tiviacz.cloudboots.init.ModArmorMaterials;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItemsImpl {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems("cloudboots");

    public static final DeferredItem<Item> CLOUD_BOOTS = ITEMS.register("cloud_boots", () -> new CloudBootsItem(ModArmorMaterials.CLOUD, 0.15D, 4));
    public static final DeferredItem<Item> IRON_CLOUD_BOOTS = ITEMS.register("iron_cloud_boots", () -> new CloudBootsItem(ModArmorMaterials.IRON_CLOUD, 0.05D, 1));
    public static final DeferredItem<Item> GOLD_CLOUD_BOOTS = ITEMS.register("gold_cloud_boots", () -> new CloudBootsItem(ModArmorMaterials.GOLD_CLOUD, 0.10D, 2));
    public static final DeferredItem<Item> DIAMOND_CLOUD_BOOTS = ITEMS.register("diamond_cloud_boots", () -> new CloudBootsItem(ModArmorMaterials.DIAMOND_CLOUD, 0.15D, 3));
    public static final DeferredItem<Item> NETHERITE_CLOUD_BOOTS = ITEMS.register("netherite_cloud_boots", () -> new CloudBootsItem(ModArmorMaterials.NETHERITE_CLOUD, 0.20D, 4));
    public static final DeferredItem<Item> GOLDEN_FEATHER = ITEMS.register("golden_feather", () -> new GoldenFeatherItem(new Item.Properties().stacksTo(1).durability(385)));

    public static Supplier<Item> getItem(ResourceLocation id) {
        return () -> BuiltInRegistries.ITEM.get(id);
    }
}
