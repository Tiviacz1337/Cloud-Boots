package com.tiviacz.cloudboots.forge.handler;

import com.tiviacz.cloudboots.CloudBoots;
import com.tiviacz.cloudboots.item.CloudBootsItem;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CloudBoots.MODID)
public class ForgeEventHandler {
    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            CloudBootsItem.tick(event.player);
        }
    }

    @SubscribeEvent
    public static void playerFallDamage(LivingAttackEvent event) {
        if(CloudBootsItem.negateFallDamage(event.getEntity(), event.getSource())) {
            event.setCanceled(true);
        }
    }
}