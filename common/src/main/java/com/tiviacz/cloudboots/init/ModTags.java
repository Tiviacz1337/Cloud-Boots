package com.tiviacz.cloudboots.init;

import com.tiviacz.cloudboots.CloudBoots;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final TagKey<Item> REPAIRS_CLOUD_ARMOR = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(CloudBoots.MODID, "repairs_cloud_armor"));
}