package com.tiviacz.cloudboots.init;

import com.tiviacz.cloudboots.CloudBoots;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ModItems {
    public static final Identifier CLOUD_BOOTS_ID = Identifier.fromNamespaceAndPath(CloudBoots.MODID, "cloud_boots");
    public static final Identifier COPPER_CLOUD_BOOTS_ID = Identifier.fromNamespaceAndPath(CloudBoots.MODID, "copper_cloud_boots");
    public static final Identifier IRON_CLOUD_BOOTS_ID = Identifier.fromNamespaceAndPath(CloudBoots.MODID, "iron_cloud_boots");
    public static final Identifier GOLD_CLOUD_BOOTS_ID = Identifier.fromNamespaceAndPath(CloudBoots.MODID, "gold_cloud_boots");
    public static final Identifier DIAMOND_CLOUD_BOOTS_ID = Identifier.fromNamespaceAndPath(CloudBoots.MODID, "diamond_cloud_boots");
    public static final Identifier NETHERITE_CLOUD_BOOTS_ID = Identifier.fromNamespaceAndPath(CloudBoots.MODID, "netherite_cloud_boots");
    public static final Identifier GOLDEN_FEATHER_ID = Identifier.fromNamespaceAndPath(CloudBoots.MODID, "golden_feather");

    public static Supplier<Item> getItem(Identifier id) {
        return () -> BuiltInRegistries.ITEM.getValue(id);
    }
}