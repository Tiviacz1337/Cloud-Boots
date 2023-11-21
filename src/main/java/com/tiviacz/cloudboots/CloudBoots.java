package com.tiviacz.cloudboots;

import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod(CloudBoots.MODID)
public class CloudBoots
{
    public static final String MODID = "cloudboots";
    private static boolean curiosLoaded;
	
    public CloudBoots()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onEnqueueIMC);

        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        curiosLoaded = ModList.get().isLoaded("curios");
    }

    private void onEnqueueIMC(InterModEnqueueEvent event)
    {
        if(!enableCurios()) return;
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CURIO.getMessageBuilder().build());
    }

    public static boolean enableCurios()
    {
        return curiosLoaded;
    }

    public static class ModItems
    {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CloudBoots.MODID);

        public static final RegistryObject<Item> CLOUD_BOOTS = ITEMS.register("cloud_boots", () -> new CloudBootsItem(CloudBootsItem.CloudArmorMaterial.CLOUD, 0.15D, 4));
        public static final RegistryObject<Item> IRON_CLOUD_BOOTS = ITEMS.register("iron_cloud_boots", () -> new CloudBootsItem(ArmorMaterials.IRON, 0.05D, 1));
        public static final RegistryObject<Item> GOLD_CLOUD_BOOTS = ITEMS.register("gold_cloud_boots", () -> new CloudBootsItem(ArmorMaterials.GOLD, 0.10D, 2));
        public static final RegistryObject<Item> DIAMOND_CLOUD_BOOTS = ITEMS.register("diamond_cloud_boots", () -> new CloudBootsItem(ArmorMaterials.DIAMOND, 0.15D, 3));
        public static final RegistryObject<Item> NETHERITE_CLOUD_BOOTS = ITEMS.register("netherite_cloud_boots", () -> new CloudBootsItem(ArmorMaterials.NETHERITE, 0.20D, 4));
        public static final RegistryObject<Item> GOLDEN_FEATHER = ITEMS.register("golden_feather", () -> new GoldenFeatherItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1).defaultDurability(385)));
    }
}