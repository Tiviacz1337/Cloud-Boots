package com.tiviacz.cloudboots.forge.compat;

import com.tiviacz.cloudboots.forge.init.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class GoldenFeatherCurio implements ICurioItem {
    public static void registerCurio() {
        CuriosApi.registerCurio(ModItems.GOLDEN_FEATHER.get(), new GoldenFeatherCurio());
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if(slotContext.entity() instanceof ServerPlayer serverPlayer) {
            if(serverPlayer.fallDistance >= 3.0F) {
                stack.hurtAndBreak(1, serverPlayer, e -> curioBreak(slotContext, stack));
            }
        }
    }
}