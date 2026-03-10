package com.tiviacz.cloudboots.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class Platform {
    @ExpectPlatform
    public static Supplier<Item> getItem(ResourceLocation id) {
        return null;
    }
}