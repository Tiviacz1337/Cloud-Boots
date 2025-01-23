package com.tiviacz.cloudboots.neoforge.compat;

import com.tiviacz.cloudboots.init.neoforge.ModItemsImpl;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public record GoldenFeatherCurio(ItemStack stack) implements ICurio {
    public static void registerCurio(RegisterCapabilitiesEvent event) {
        ModItemsImpl.ITEMS.getEntries().stream()
                .filter(holder -> holder.get() == ModItemsImpl.GOLDEN_FEATHER.get())
                .forEach(holder -> event.registerItem(CuriosCapability.ITEM, (stack, context) -> new GoldenFeatherCurio(stack), holder::get));
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
                serverPlayer.fallDistance = 0.0F;

                if(!serverPlayer.level().isClientSide && serverPlayer.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.CLOUD, serverPlayer.xOld, serverPlayer.yOld, serverPlayer.zOld, 3, 0, 0, 0, (serverPlayer.level().random.nextFloat() - 0.5F));
                }
            }
        }
    }
}
