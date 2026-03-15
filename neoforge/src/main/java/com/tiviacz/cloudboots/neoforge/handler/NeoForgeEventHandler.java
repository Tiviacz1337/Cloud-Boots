package com.tiviacz.cloudboots.neoforge.handler;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.item.CloudBootsItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = CloudBoots.MODID)
public class NeoForgeEventHandler {
    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Post event) {
        CloudBootsItem.tick(event.getEntity());
    }

    @SubscribeEvent
    public static void playerFallDamage(LivingIncomingDamageEvent event) {
        if(CloudBootsItem.negateFallDamage(event.getEntity(), event.getSource())) {
            event.setCanceled(true);
        }
    }
}