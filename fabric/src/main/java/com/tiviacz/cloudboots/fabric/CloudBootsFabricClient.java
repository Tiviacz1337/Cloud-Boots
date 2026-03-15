package com.tiviacz.cloudboots.fabric;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.config.CloudBootsConfig;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.client.ConfigScreenFactoryRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;

public final class CloudBootsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NeoForgeConfigRegistry.INSTANCE.register(CloudBoots.MODID, ModConfig.Type.CLIENT, CloudBootsConfig.clientSpec);
        ConfigScreenFactoryRegistry.INSTANCE.register(CloudBoots.MODID, ConfigurationScreen::new);
    }
}