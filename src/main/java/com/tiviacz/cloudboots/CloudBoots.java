package com.tiviacz.cloudboots;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = CloudBoots.MODID, name = CloudBoots.NAME, version = CloudBoots.VERSION)
@EventBusSubscriber
public class CloudBoots
{	
	public static final String MODID = "cloudboots";
	public static final String NAME = "Cloud Boots Mod";
	public static final String VERSION = "1.4.1";

	public static final Item CLOUD_BOOTS = new ItemCloudBoots();
	public static final Item GOLDEN_FEATHER = new ItemGoldenFeather();
	
	@SubscribeEvent
	public static void onItemRegister(Register<Item> event)
	{
		event.getRegistry().register(CLOUD_BOOTS);
		event.getRegistry().register(GOLDEN_FEATHER);
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		ModelLoader.setCustomModelResourceLocation(CLOUD_BOOTS, 0, new ModelResourceLocation(CLOUD_BOOTS.getRegistryName(), "inventory"));	
		ModelLoader.setCustomModelResourceLocation(GOLDEN_FEATHER, 0, new ModelResourceLocation(GOLDEN_FEATHER.getRegistryName(), "inventory"));	
	}
}