package com.tiviacz.cloudboots.forge.init;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.item.CloudBootsItem;
import com.tiviacz.cloudboots.item.GoldenFeatherItem;
import com.tiviacz.cloudboots.item.armor.DefaultArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CloudBoots.MODID);

    public static final RegistryObject<Item> CLOUD_BOOTS = ITEMS.register("cloud_boots", () -> new CloudBootsItem(new DefaultArmorMaterial(ArmorMaterials.DIAMOND, "cloud"), new Item.Properties()));
    public static final RegistryObject<Item> IRON_CLOUD_BOOTS = ITEMS.register("iron_cloud_boots", () -> new CloudBootsItem(new DefaultArmorMaterial(ArmorMaterials.IRON, "iron"), new Item.Properties()));
    public static final RegistryObject<Item> GOLD_CLOUD_BOOTS = ITEMS.register("gold_cloud_boots", () -> new CloudBootsItem(new DefaultArmorMaterial(ArmorMaterials.GOLD, "gold"), new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_CLOUD_BOOTS = ITEMS.register("diamond_cloud_boots", () -> new CloudBootsItem(new DefaultArmorMaterial(ArmorMaterials.DIAMOND, "diamond"), new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_CLOUD_BOOTS = ITEMS.register("netherite_cloud_boots", () -> new CloudBootsItem(new DefaultArmorMaterial(ArmorMaterials.NETHERITE, "netherite"), new Item.Properties()));
    public static final RegistryObject<Item> GOLDEN_FEATHER = ITEMS.register("golden_feather", () -> new GoldenFeatherItem(new Item.Properties()));
}