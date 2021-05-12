package com.tiviacz.cloudboots;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;

@Mod(CloudBoots.MODID)
public class CloudBoots
{
    public static final String MODID = "cloudboots";
	
    public CloudBoots()
    {
        CloudBootsConfigurator cfg = new CloudBootsConfigurator();

        try {
            cfg.executeConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static class ModItems
    {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CloudBoots.MODID);

        public static final RegistryObject<Item> CLOUD_BOOTS = ITEMS.register("cloud_boots", () -> new CloudBootsItem(new Item.Properties().group(ItemGroup.COMBAT)));
        public static final RegistryObject<Item> GOLDEN_FEATHER = ITEMS.register("golden_feather", () -> new GoldenFeatherItem(new Item.Properties().group(ItemGroup.MISC).maxStackSize(1).defaultMaxDamage(CloudBootsConfigurator.goldenFeatherDurability)));
    }
}