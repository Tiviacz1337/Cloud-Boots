package com.tiviacz.cloudboots.init;

import com.tiviacz.cloudboots.CloudBoots;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Contract;

import java.util.function.Supplier;

public class ModItems {
    public static final ResourceLocation CLOUD_BOOTS = ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "cloud_boots");
    public static final ResourceLocation IRON_CLOUD_BOOTS = ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "iron_cloud_boots");
    public static final ResourceLocation GOLD_CLOUD_BOOTS = ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "gold_cloud_boots");
    public static final ResourceLocation DIAMOND_CLOUD_BOOTS = ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "diamond_cloud_boots");
    public static final ResourceLocation GOLDEN_FEATHER = ResourceLocation.fromNamespaceAndPath(CloudBoots.MODID, "golden_feather");

    @Contract
    @ExpectPlatform
    public static Supplier<Item> getItem(ResourceLocation id) {
        return null;
    }
}
