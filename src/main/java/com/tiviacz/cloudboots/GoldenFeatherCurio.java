package com.tiviacz.cloudboots;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class GoldenFeatherCurio implements ICurio
{
    private final ItemStack stack;

    public static ICurio createGoldenFeatherCurioProvider(ItemStack stack)
    {
        return new GoldenFeatherCurio(stack);
    }

    public GoldenFeatherCurio(ItemStack stack)
    {
        this.stack = stack;
    }

    @Override
    public ItemStack getStack() {
        return this.stack;
    }

    @Override
    public void curioTick(SlotContext context)
    {
        if(context.entity() instanceof ServerPlayer serverPlayer)
        {
            if(serverPlayer.fallDistance >= 3.0F)
            {
                stack.hurtAndBreak(1, serverPlayer, e -> curioBreak(context));
                serverPlayer.fallDistance = 0.0F;

                if(!serverPlayer.level.isClientSide && serverPlayer.level instanceof ServerLevel serverLevel)
                {
                    serverLevel.sendParticles(ParticleTypes.CLOUD, serverPlayer.xOld, serverPlayer.yOld, serverPlayer.zOld, 3, 0, 0, 0, (serverPlayer.level.random.nextFloat() - 0.5F));
                }
            }
        }
    }
}
