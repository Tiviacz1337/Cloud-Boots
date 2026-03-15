package com.tiviacz.cloudboots.neoforge.compat;

import com.tiviacz.cloudboots.neoforge.init.ModItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public record GoldenFeatherCurio(ItemStack stack) implements ICurio {
    public static void registerCurio(RegisterCapabilitiesEvent event) {
        event.registerItem(CuriosCapability.ITEM, (stack, context) -> new GoldenFeatherCurio(stack), ModItems.GOLDEN_FEATHER.get());
    }

    @Override
    public ItemStack getStack() {
        return this.stack;
    }

    @Override
    public void curioTick(SlotContext context) {
        if(context.entity() instanceof ServerPlayer serverPlayer) {
            if(serverPlayer.fallDistance >= 3.0F) {
                stack.hurtAndBreak(1, serverPlayer.serverLevel(), serverPlayer, e -> curioBreak(context));
            }
        }
    }
}